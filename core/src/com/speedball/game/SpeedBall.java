package com.speedball.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Testing committing to Git with Eclipse
 * @author Calvin Yarboro
 *
 */
public class SpeedBall extends ApplicationAdapter {
	private static final float MAX_X = 1000f;
	private static final float MAX_Y = 645f;
	private static final float PLAYER_WIDTH = 80.0f;
	private static final float PLAYER_HEIGHT = 58.4f;
	private static final float PLAYER_CENTER_WIDTH = PLAYER_WIDTH / 2;
	private static final float PLAYER_CENTER_HEIGHT = PLAYER_HEIGHT / 2;
	private static final float PLAYER_GUN_HEIGHT = PLAYER_CENTER_HEIGHT - 5;
	private static final float PLAYER_GUN_WIDTH = 70;
	private static final float WALK_SPEED = 150.0f;
	private static final float SPRINT_SPEED = 200.0f;
	private static final float PAINTBALL_SPEED = 1.0f;
	private static final float INIT_X = 0.0f;
	private static final float INIT_Y = 0.0f;
	private static final float GUN_RADIUS = (float)Math.sqrt(Math.pow(PLAYER_GUN_WIDTH - PLAYER_CENTER_WIDTH, 2) + Math.pow(PLAYER_CENTER_HEIGHT - PLAYER_GUN_HEIGHT, 2));
	
	private Utils utils = new Utils();
	private GunUtils gunUtils = new GunUtils();
	
	private SpriteBatch batch;
	private Texture img;
	private float playerSpeed;
	private Sprite player;
	private Sprite background;
	private ArrayList<PaintballSprite> paintballs;
	private int paintballCounter;
	private float playerX;
	private float playerY;
	private float gunX;
	private float gunY;
	private float mouseAngle;

	private Cursor customCursor;
	private Texture cursor;
	

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = utils.createPlayerSprite();
		background = utils.createBackgroundSprite();
		paintballCounter = -1;
		paintballs = new ArrayList<PaintballSprite>();
		playerX = INIT_X;
		playerY = INIT_Y;
		cursor = new Texture("crossHair.PNG");
		customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("crossHair.PNG")), cursor.getWidth()/2, cursor.getHeight()/2);
		Gdx.graphics.setCursor(customCursor);

	}

	@Override
	public void render () {
	    Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		updateSpriteState();
		updateGameState();
		batch.end();
	}
	
	/*
	 * Need to look into making sure we are disposing the images properly
	 * @see com.badlogic.gdx.ApplicationAdapter#dispose()
	 */
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	
	private void updateSpriteState() {
	    background.setBounds(0, 0, 1080, 720);
        background.draw(batch);
        gunUtils.drawPaintballs(batch, paintballs, PAINTBALL_SPEED);
        player.setBounds(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
        mouseAngle = utils.getMouseAngle(playerX, playerY, PLAYER_CENTER_WIDTH, PLAYER_CENTER_HEIGHT);
        player = utils.rotateSprite(mouseAngle, player, PLAYER_CENTER_WIDTH, PLAYER_CENTER_HEIGHT, playerX, playerY);
        player.draw(batch);
        
	}
	private void updateGameState() {
	    if(checkAndFireGun(mouseAngle)) {
            paintballs.get(paintballCounter).setBounds(gunX, gunY, 10, 10);
            paintballs.set(paintballCounter, (PaintballSprite) utils.rotateSprite(mouseAngle, paintballs.get(paintballCounter), PLAYER_CENTER_WIDTH - PLAYER_GUN_WIDTH, PLAYER_CENTER_HEIGHT - PLAYER_GUN_HEIGHT, playerX, playerY));
            paintballs.get(paintballCounter).draw(batch);
        }
	    //checks to make sure player is in bounds, and calls movePlayer
	    checkAndMovePlayer(playerX, playerY, MAX_X, MAX_Y);
	}
	private boolean checkAndFireGun(float angle) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            //sets gun position before roation
            gunX = gunUtils.getPlayerGunCoord(playerX, PLAYER_GUN_WIDTH, true);
            gunY = gunUtils.getPlayerGunCoord(playerY, PLAYER_GUN_HEIGHT, false);
            //Get the current center of the player
            float centerX = playerX + PLAYER_CENTER_WIDTH;
            float centerY = playerY + PLAYER_CENTER_HEIGHT;
            //Get the array of gun coordinates based on the angle, center, and radius
            float[] coords = gunUtils.updateRealGunXY(angle, centerX, centerY, GUN_RADIUS);
            //Get the current mouse coordinates
            float mouseX = Gdx.input.getX();
            float mouseY = Math.abs(720 - Gdx.input.getY());
            //Get the slope of between the gun coordinates and mouse coordinates 
            float slope = (mouseY - coords[1]) / (mouseX - coords[0]);
            //Find what quadrant our mouse is in based on the angle
            int quadrant = gunUtils.checkQuadrant(angle);
            //Add the paintball into the ArrayList with it's current state
            paintballs.add(gunUtils.createPaintballSprite(gunX, gunY, slope, quadrant));
            /*System.out.println("ANGLE: " + angle + " SLOPE: " + slope + " realgunX: " + coords[0] + " realgunY: " + coords[1] + 
                " fakeGunX: "+ gunX + " fakeGunY: " + gunY + " playerX: " + playerX + " playerY:" + playerY + " Quadrant: " + quadrant);*/
            paintballCounter++;
            return true;
        }
       return false;
        
    }

	private void checkAndMovePlayer(float x, float y, float maxX, float maxY) {
		playerSpeed = utils.setPlayerSpeed(SPRINT_SPEED, WALK_SPEED);
		if (utils.playerInBounds(x, y, maxX, maxY)) {
			float[] playerXY = utils.movePlayer(x, y, playerSpeed);
			playerX = playerXY[0];
			playerY = playerXY[1];
		}
		else {
			playerX = utils.resetPlayerAtXBound(x, maxX);
			playerY = utils.resetPlayerAtYBound(y, maxY);
		}
	}
}

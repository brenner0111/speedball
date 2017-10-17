package com.speedball.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Testing committing to Git with Eclipse
 * @author Calvin Yarboro
 *
 */
public class SpeedBall extends ApplicationAdapter {
	private static final float MAX_X = 1045f;
	private static final float MAX_Y = 690f;
	private static final float PLAYER_WIDTH = 36.0f;
	private static final float PLAYER_HEIGHT = 26.28f;
	private static final float PLAYER_CENTER_WIDTH = PLAYER_WIDTH / 2;
	private static final float PLAYER_CENTER_HEIGHT = PLAYER_HEIGHT / 2;
	private static final float PLAYER_GUN_HEIGHT = PLAYER_CENTER_HEIGHT - 2;
	private static final float PLAYER_GUN_WIDTH = 34;
	private static final float WALK_SPEED = 150.0f;
	private static final float SPRINT_SPEED = 200.0f;
	private static final float PAINTBALL_SPEED = 1.0f;
	private static final float INIT_X = 0.0f;
	private static final float INIT_Y = 0.0f;
	private static final float GUN_RADIUS = (float)Math.sqrt(Math.pow(PLAYER_GUN_WIDTH - PLAYER_CENTER_WIDTH, 2) + Math.pow(PLAYER_CENTER_HEIGHT - PLAYER_GUN_HEIGHT, 2));
	
	private Utils utils = new Utils();
	private GunUtils gunUtils = new GunUtils();
	private Bunker bunker = new Bunker();
	
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
	
	private OrthographicCamera camera;
	private final float GAME_WORLD_WIDTH = 1080;
	private final float GAME_WORLD_HEIGHT = 720;
	
	Viewport viewport;
	

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = utils.createPlayerSprite();
		player.setPosition(0, 0);
		
		background = utils.createBackgroundSprite();
		background.setPosition(0, 0);
		background.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
		
		camera = new OrthographicCamera();
		viewport =  new StretchViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera);
		viewport.apply();
		camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
				
		paintballCounter = -1;
		paintballs = new ArrayList<PaintballSprite>();
		playerX = INIT_X;
		playerY = INIT_Y;
		cursor = new Texture("misc/crossHair.PNG");
		customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("misc/crossHair.PNG")), cursor.getWidth()/2, cursor.getHeight()/2);
		Gdx.graphics.setCursor(customCursor);
		
		bunker.createBunkers();

	}
	
	@Override
	public void resize(int width, int height) {
	    viewport.update(width, height);
	    camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
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
		camera.update();
		batch.setProjectionMatrix(camera.combined);
        background.draw(batch);
        bunker.drawBunkers(batch);
        gunUtils.drawPaintballs(batch, paintballs, PAINTBALL_SPEED);
        player.setBounds(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
        mouseAngle = utils.getMouseAngle(playerX, playerY, PLAYER_CENTER_WIDTH, PLAYER_CENTER_HEIGHT, camera);
        player = utils.rotateSprite(mouseAngle, player, PLAYER_CENTER_WIDTH, PLAYER_CENTER_HEIGHT, playerX, playerY);
        player.draw(batch);
        
	}
	private void updateGameState() {
	    if(checkAndFireGun(mouseAngle)) {
            paintballs.get(paintballCounter).setBounds(gunX, gunY, 4, 4);
            paintballs.set(paintballCounter, (PaintballSprite) utils.rotateSprite(mouseAngle, paintballs.get(paintballCounter), PLAYER_CENTER_WIDTH - PLAYER_GUN_WIDTH, PLAYER_CENTER_HEIGHT - PLAYER_GUN_HEIGHT, playerX, playerY));
            paintballs.get(paintballCounter).draw(batch);
        }
	    //checks to make sure player is in bounds, and calls movePlayer
	    checkAndMovePlayer(playerX, playerY, MAX_X, MAX_Y);
	}
	private boolean checkAndFireGun(float angle) {
		// If screen was just touched or left click was pushed on desktop
	    // Gdx.input.isButtonPressed(Input.Buttons.LEFT)
		if (Gdx.input.justTouched()) {
            //sets gun position before rotation
            gunX = gunUtils.getPlayerGunCoord(playerX, PLAYER_GUN_WIDTH, true);
            gunY = gunUtils.getPlayerGunCoord(playerY, PLAYER_GUN_HEIGHT, false);
            //Get the current center of the player
            float centerX = playerX + PLAYER_CENTER_WIDTH;
            float centerY = playerY + PLAYER_CENTER_HEIGHT;
            //Get the array of gun coordinates based on the angle, center, and radius
            float[] coords = gunUtils.updateRealGunXY(angle, centerX, centerY, GUN_RADIUS);
            //Get the current mouse coordinates
            float mouseX = Gdx.input.getX();
            Vector3 tmpCoords = new Vector3(mouseX,Gdx.input.getY(), 0);
            camera.unproject(tmpCoords);
            //Get the slope of between the gun coordinates and mouse coordinates 
            float slope = (tmpCoords.y - coords[1]) / (tmpCoords.x - coords[0]);
            //Find what quadrant our mouse is in based on the angle
            int quadrant = gunUtils.checkQuadrant(angle);
            //Add the paintball into the ArrayList with it's current state
            paintballs.add(gunUtils.createPaintballSprite(gunX, gunY, slope, quadrant));
            System.out.println("ANGLE: " + angle + " SLOPE: " + slope + " realgunX: " + coords[0] + " realgunY: " + coords[1] + 
                " fakeGunX: "+ gunX + " fakeGunY: " + gunY + " playerX: " + playerX + " playerY:" + playerY + " Quadrant: " + quadrant +
                " MouseXY: (" + Gdx.input.getX() + ", " + Gdx.input.getY() + ")" + "MappedMouseXY: (" + tmpCoords.x + "f, " + tmpCoords.y + "f,)" );
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

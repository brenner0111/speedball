package com.speedball.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
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
	private static final int PLAYER_WIDTH = 80;
	private static final int PLAYER_HEIGHT = 52;
	private static final int PLAYER_CENTER_WIDTH = PLAYER_WIDTH / 2;
	private static final int PLAYER_CENTER_HEIGHT = PLAYER_HEIGHT / 2;
	private static final int PLAYER_GUN_HEIGHT = 9;
	private static final int PLAYER_GUN_WIDTH = 70;
	private static final float WALK_SPEED = 150.0f;
	private static final float SPRINT_SPEED = 200.0f;

	
	Utils utils = new Utils();
	GunUtils gunUtils = new GunUtils();
	SpriteBatch batch;
	Texture img;
	float playerSpeed;
	private Sprite player;
	private Sprite background;
	private ArrayList<PaintballSprite> paintballs;
	private int paintballCounter;
	float playerX;
	float playerY;
	float gunX;
	float gunY;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		player = utils.createPlayerSprite();
		background = utils.createBackgroundSprite();
		paintballCounter = -1;
		paintballs = new ArrayList<PaintballSprite>();
		playerX = 0.0f;
		playerY = 0.0f;

	}

	@Override
	// Player moves faster when moving diagonally
	public void render () {
		//checks to make sure player is in bounds, and calls movePlayer
		checkAndMovePlayer(playerX, playerY, MAX_X, MAX_Y);
		
		
	    Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		batch.begin();
		background.setBounds(0, 0, 1080, 720);
		background.draw(batch);
		gunUtils.drawPaintballs(batch, paintballs);
		player.setBounds((int)playerX, (int)playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
		float angle = utils.getMouseAngle((int)playerX, (int)playerY, PLAYER_CENTER_WIDTH, PLAYER_CENTER_HEIGHT);
		player = utils.rotateSprite(angle, player, PLAYER_CENTER_WIDTH, PLAYER_CENTER_HEIGHT);
		player.draw(batch);
		if(checkAndFireGun()) {
			
			paintballs.get(paintballCounter).setBounds(gunX, gunY, 10, 10);
			paintballs.set(paintballCounter, (PaintballSprite) utils.rotateSprite(angle, paintballs.get(paintballCounter), PLAYER_CENTER_WIDTH - PLAYER_GUN_WIDTH, PLAYER_CENTER_HEIGHT - PLAYER_GUN_HEIGHT));
			paintballs.get(paintballCounter).draw(batch);
		}
		System.out.println("GunX: " + gunX + " GunY: " + gunY);
		System.out.println("PlayerX " + playerX + " PlayerY" + playerY);
		test();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	

	private boolean checkAndFireGun() {
	    if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
	        gunX = gunUtils.getPlayerGunCoord(playerX, PLAYER_GUN_WIDTH, true);
	        gunY = gunUtils.getPlayerGunCoord(playerY, PLAYER_GUN_HEIGHT, false);
	        paintballs.add(gunUtils.createPaintballSprite(gunX, gunY));
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
	
	//Print statements to run in loops
	protected void test() {
		System.out.println("paintballs AL: " + paintballs);
		System.out.println("paintballs AL Length:" + paintballs.size());
		if (paintballs.size() != 0) {
			System.out.println("collided: " + paintballs.get(0).getCollided());
		}
	}
	
}

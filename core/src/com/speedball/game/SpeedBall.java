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
	private static final float PLAYER_WIDTH = 80;
	private static final float PLAYER_HEIGHT = 52;
	private static final float PLAYER_CENTER_WIDTH = PLAYER_WIDTH / 2;
	private static final float PLAYER_CENTER_HEIGHT = PLAYER_HEIGHT / 2;
	private static final float PLAYER_GUN_HEIGHT = 9;
	private static final float PLAYER_GUN_WIDTH = 70;
	private static final float WALK_SPEED = 150.0f;
	private static final float SPRINT_SPEED = 200.0f;
	private static final float PAINTBALL_SPEED = 1.0f;
	private static final float INIT_X = 0.0f;
	private static final float INIT_Y = 0.0f;
	private static final float PLAYER_RADIUS = (float)Math.sqrt(Math.pow(PLAYER_CENTER_WIDTH, 2) + Math.pow(PLAYER_CENTER_HEIGHT, 2));
	
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
	float rotatedPlayerX;
	float rotatedPlayerY;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		player = utils.createPlayerSprite();
		background = utils.createBackgroundSprite();
		paintballCounter = -1;
		paintballs = new ArrayList<PaintballSprite>();
		playerX = INIT_X;
		playerY = INIT_Y;

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
		//gunUtils.drawPaintballs(batch, paintballs, PAINTBALL_SPEED);
		player.setBounds(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
		float angle = utils.getMouseAngle(playerX, playerY, PLAYER_GUN_WIDTH, PLAYER_GUN_HEIGHT);
		System.out.println("playerX: " + playerX + " PlayerY: " + playerY);
//		float newGunX = gunX;
//		float newGunY = gunY;
//		if (paintballCounter != -1) {
//			PaintballSprite current = paintballs.get(paintballCounter);
//			newGunX = current.getRotatedX();
//			newGunY = current.getRotatedY();
//		}
////		float gunAngle = gunUtils.getMouseAngle(newGunX, newGunY);
//		float mouseX = Gdx.input.getX();
//        float mouseY = Math.abs(720 - Gdx.input.getY());
		//testing if updating player x and y with rotation will help fix rotation for the gun 
		/*float a_ = PLAYER_CENTER_WIDTH;
        float b_ = PLAYER_CENTER_HEIGHT;
        float playerRadius = (float) Math.sqrt(Math.pow(a_, 2) + Math.pow(b_, 2));
        float playerRotatedX = (PLAYER_CENTER_WIDTH + playerX) + (playerRadius * (float)Math.cos(Math.toRadians(angle)));
        float playerRotatedY = (PLAYER_CENTER_HEIGHT + playerY) + (playerRadius * (float)Math.sin(Math.toRadians(angle)));

        System.out.println("Player Rotated X: " + playotatedX + " Player Rotated Y: " + playerRotatedY);
		System.out.println("playerRadius: " + playerRadius);*/
		
		if (playerX != INIT_X || playerY != INIT_Y) {
		    
			player = utils.rotateSprite(angle, player, PLAYER_CENTER_WIDTH, PLAYER_CENTER_HEIGHT, playerX, playerY);
			
//			rotatedPlayerX = utils.updatePlayerX(playerX, playerY, angle, playerX + PLAYER_CENTER_WIDTH, PLAYER_RADIUS);
//			rotatedPlayerY = utils.updatePlayerY(playerX, playerY, angle, playerY + PLAYER_CENTER_HEIGHT, PLAYER_RADIUS);
//			System.out.println("RotatedPlayerX: " + rotatedPlayerX + " RotatedPlayerY: " + rotatedPlayerY);
//			System.out.println("Angle: " + angle + " Radius: " + PLAYER_RADIUS);
//			
		}
		player.draw(batch);
		gunUtils.drawPaintballs(batch, paintballs, PAINTBALL_SPEED);
		if(checkAndFireGun(angle)) {
		    //float gunRadius = PLAYER_GUN_WIDTH - PLAYER_CENTER_WIDTH;
		    /*float b = PLAYER_GUN_WIDTH - PLAYER_CENTER_WIDTH;
		    float a = PLAYER_CENTER_HEIGHT - PLAYER_GUN_HEIGHT;
		    float gunRadius = (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
			float x =  (PLAYER_CENTER_WIDTH + playerX) + (b * (float)Math.cos(Math.toRadians(angle)));
			float y = (PLAYER_CENTER_HEIGHT + playerY) + (b * (float)Math.sin(Math.toRadians(angle)));
			System.out.println("gunRadius: " + gunRadius);
			System.out.println("Player Origin: (" + (PLAYER_CENTER_WIDTH + playerX) + ", " + (PLAYER_CENTER_HEIGHT + playerY) + ")");*/
	
			/*float x =  (PLAYER_CENTER_WIDTH + playerRotatedX) + (gunRadius * (float)Math.cos(Math.toRadians(angle)));
            float y = (PLAYER_CENTER_HEIGHT + playerRotatedY) + (gunRadius * (float)Math.sin(Math.toRadians(angle)));*/
			//float diff = PLAYER_CENTER_HEIGHT - PLAYER_GUN_HEIGHT;
			//y = y - diff;
			
			
			//float x = (gunX * (float)Math.cos(Math.toRadians(angle))) - (gunY * (float)Math.sin(Math.toRadians(angle)));
			//float y = (gunY * (float)Math.cos(Math.toRadians(angle))) + (gunX * (float)Math.sin(Math.toRadians(angle)));
			
			
//			float tempAngle = angle - 29.5f;
//			float x = ((float)Math.cos(Math.toRadians(angle)) * (gunX- (playerX + PLAYER_CENTER_WIDTH))) - ((float)Math.sin(Math.toRadians(angle)) * (gunY - (playerY + PLAYER_CENTER_HEIGHT)));
//			float y = ((float)Math.sin(Math.toRadians(angle)) * (gunX- (playerX + PLAYER_CENTER_WIDTH))) + ((float)Math.cos(Math.toRadians(angle)) * (gunY - (playerY + PLAYER_CENTER_HEIGHT)));
//			gunX = x + (playerX + PLAYER_CENTER_WIDTH);
//			gunY = y + (playerY + PLAYER_CENTER_HEIGHT); 
			
			
			
			
			
		    paintballs.get(paintballCounter).setBounds(gunX, gunY, 10, 10);
			paintballs.set(paintballCounter, (PaintballSprite) utils.rotateSprite(angle, paintballs.get(paintballCounter), PLAYER_CENTER_WIDTH - PLAYER_GUN_WIDTH, PLAYER_CENTER_HEIGHT - PLAYER_GUN_HEIGHT, playerX, playerY));
			//paintballs.set(paintballCounter, (PaintballSprite) utils.rotateSprite(angle, paintballs.get(paintballCounter), (float)(playerX + PLAYER_CENTER_WIDTH), (float)(playerY + PLAYER_CENTER_HEIGHT)));
//			float slope = gunUtils.getSlope(newGunX, newGunY, mouseX, mouseY);
//			paintballs.get(paintballCounter).setSlope(slope);
			paintballs.get(paintballCounter).draw(batch);
			
			System.out.println("gunX: " + gunX + " gunY: " + gunY);
			System.out.println("ANGLE: " + angle);
			//System.out.println("gunAngle : " + gunAngle);
			//System.out.println("tempAngle: " + tempAngle);
			
		}
		
		test();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	
	private boolean checkAndFireGun(float angle) {
	    if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
	        gunX = gunUtils.getPlayerGunCoord(playerX, PLAYER_GUN_WIDTH, true);
	        gunY = gunUtils.getPlayerGunCoord(playerY, PLAYER_GUN_HEIGHT, false);
	        float mouseX = Gdx.input.getX();
	        float mouseY = Math.abs(720 - Gdx.input.getY());
	        System.out.println("MouseX: " + mouseX + " MouseY: " + mouseY);
	        float slope = (mouseY - gunY) / (mouseX - gunX);
	        //int quadrant = gunUtils.checkQuadrant(gunX, gunY, mouseX, mouseY);
	        int quadrant = gunUtils.checkQuadrant(angle);
	        paintballs.add(gunUtils.createPaintballSprite(gunX, gunY, slope, quadrant));
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
	
	public void setPlayerX(float playerX) {
		this.playerX = playerX;
	}
	public void setPlayerY(float playerY) {
		this.playerY = playerY;
	}
	
	//Print statements to run in loops
	protected void test() {
	//	System.out.println("Sprite Rotation:" + player.getRotation());
//		if(paintballs.size() != 0) {
//			System.out.println("paintballs newGunX: " + paintballs.get(paintballCounter).getRotatedX());
//			System.out.println("paintballs newGunY: " + paintballs.get(paintballCounter).getRotatedY());
//		}
//		System.out.println("paintballs AL Length:" + paintballs.size());
//		if (paintballs.size() != 0) {
//			System.out.println("collided: " + paintballs.get(0).getCollided());
//		}
	}
	
}

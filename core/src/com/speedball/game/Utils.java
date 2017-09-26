package com.speedball.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Utils {
	/**
	 * Begin utility functions
	 * TODO: Possibly move to separate utility class
	 */

	protected Sprite createPlayerSprite() {
		//FileHandle playerFileHandle = Gdx.files.internal("shotgun/idle/survivor-idle_shotgun_0.png");
		//FileHandle playerFileHandle = Gdx.files.internal("red-rectangle-md.png");
	    FileHandle playerFileHandle = Gdx.files.internal("survivor-idle_shotgun_test.png");
		Texture playerTexture = new Texture(playerFileHandle);
		return new Sprite(playerTexture);
	}
	protected Sprite createBackgroundSprite() {
		FileHandle backgroundFileHandle = Gdx.files.internal("grass.png");
		Texture backgroundTexture = new Texture(backgroundFileHandle);
		return new Sprite(backgroundTexture);
	}
	/*
	 * Function that determines the player's x and y position.
	 * Player Inputs = WASD keys
	 */
	protected float[] movePlayer(float playerX, float playerY, float playerSpeed) {
		float[] array = new float[2];
		float dt = Gdx.graphics.getDeltaTime();
		float halfPlayerSpeed = (playerSpeed * 0.7f);
		if (Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.W)) {
			playerX -= dt * halfPlayerSpeed;
			playerY += dt * halfPlayerSpeed;
		}
		else if (Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.W)) {
			playerX += dt * halfPlayerSpeed;
			playerY += dt * halfPlayerSpeed;
		}
		else if (Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.S)) {
			playerX -= dt * halfPlayerSpeed;
			playerY -= dt * halfPlayerSpeed;
		}
		else if (Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.S)) {
			playerX += dt * halfPlayerSpeed;
			playerY -= dt * halfPlayerSpeed;
		}
		else if(Gdx.input.isKeyPressed(Keys.W)) {
	    		playerY += dt * playerSpeed;
		}
		else if(Gdx.input.isKeyPressed(Keys.S)) {
			playerY -= dt * playerSpeed;
		}
		else if(Gdx.input.isKeyPressed(Keys.A)) {
			playerX -= dt * playerSpeed;
		}
		else if(Gdx.input.isKeyPressed(Keys.D)) {
			playerX += dt * playerSpeed;
		}
		array[0] = playerX;
		array[1] = playerY;
		return array;
	}
	
	protected boolean playerInBounds(float x, float y, float MAX_X, float MAX_Y) {
		if (x >= 0 && x <= MAX_X && y >= 0 && y <= MAX_Y) {
			return true;
		}
		return false;
	}
	
	// Function used to reposition player if out of bounds
	protected float resetPlayerAtXBound(float x, float MAX_X) {
		if (x - MAX_X > 0) {
			return MAX_X;
		}
		else if (x <= 0) {
			return 0;
		}
		return x;
	}
  
	protected float resetPlayerAtYBound(float y, float MAX_Y) {
		if (y - MAX_Y > 0) {
			return MAX_Y;
		}
		else if (y <= 0) {
			return 0;
		}
		return y;
	}
	
	protected float getPlayerCenter(float coordinate, float offSet) {
		return coordinate + offSet;
	}
	
	
	protected float getMouseAngle(float playerX, float playerY, float offSetX, float offSetY) {
		float xCenter = getPlayerCenter(playerX, offSetX);
		float yCenter = getPlayerCenter(playerY, offSetY);
		float xCursor = Gdx.input.getX();
		float yCursor = Math.abs(720 - Gdx.input.getY());
		float deltaX = xCursor - xCenter;
		float deltaY = yCenter - yCursor;
		double theta_radians = Math.atan2(deltaY, deltaX);
		double degrees = Math.toDegrees(theta_radians);
		if (degrees < 0) {
			return (float)Math.abs(degrees);
		}
		else if (degrees > 0) {
			return (float)(360 - degrees);
		}
		
		return (float)degrees;
	} 
	protected boolean isPlayerSprinting() {
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			return true;
		}
		return false;
	}
	protected float setPlayerSpeed(float sprint, float walk) {
		if (isPlayerSprinting()) {
			return sprint;
		}
		return walk;
	}
	
	protected Sprite rotateSprite(float angle, Sprite sprite, float offSetX, float offSetY, float playerX, float playerY) {
		
		

		sprite.setOrigin(offSetX, offSetY);
		sprite.setRotation(angle);
		return sprite;
	}
	
	protected float updatePlayerX(float playerX, float playerY, float angle, float originX, float radius) {
		//return playerX + originX + (radius * (float) Math.sin(Math.toRadians(angle)));
		float s = (float)Math.sin(Math.toRadians(angle));
		float c = (float)Math.cos(Math.toRadians(angle));
		playerX -= originX;
		float newX = (playerX * c) - (playerY * s) + originX;
		return newX;
		
	}
	protected float updatePlayerY(float playerX, float playerY, float angle, float originY, float radius) {
		//return playerY + originY + (radius * (float) Math.cos(Math.toRadians(angle)));
		float s = (float)Math.sin(Math.toRadians(angle));
		float c = (float)Math.cos(Math.toRadians(angle));
		playerY -= originY;
		float newY = (playerX * s) + (playerY * c) + originY;
		return newY;
		
	}
}

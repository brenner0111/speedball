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
		FileHandle playerFileHandle = Gdx.files.internal("shotgun/idle/survivor-idle_shotgun_0.png");
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
	
	protected float movePlayerX(float playerX, float playerSpeed) {
		
		if(Gdx.input.isKeyPressed(Keys.A)) {
			playerX -= Gdx.graphics.getDeltaTime() * playerSpeed;
		}
		else if(Gdx.input.isKeyPressed(Keys.D)) {
	    	playerX += Gdx.graphics.getDeltaTime() * playerSpeed;
		}
		
		return playerX;
	}
	
	protected float movePlayerY(float playerY, float playerSpeed) {
		
		if(Gdx.input.isKeyPressed(Keys.W)) {
	    	playerY += Gdx.graphics.getDeltaTime() * playerSpeed;
		}
		else if(Gdx.input.isKeyPressed(Keys.S)) {
	    	playerY -= Gdx.graphics.getDeltaTime() * playerSpeed;
		}
		return playerY;
	}
	
	protected boolean playerInBounds(int x, int y, int MAX_X, int MAX_Y) {
		if (x >= 0 && x <= MAX_X && y >= 0 && y <= MAX_Y) {
			return true;
		}
		return false;
	}
	
	// Function used to reposition player if out of bounds
	protected float resetPlayerAtXBound(int x, int MAX_X) {
		
		if (x - MAX_X > 0) {
			return MAX_X;
		}
		else if (x <= 0) {
			return 0;
		}
		
		return x;
	}
	protected float resetPlayerAtYBound(int y, int MAX_Y) {
		
		if (y - MAX_Y > 0) {
			return MAX_Y;
		}
		else if (y <= 0) {
			return 0;
		}
		return y;
	}
	
	protected int getPlayerCenter(int coordinate, int offSet) {
		return coordinate + offSet;
	}
	
	
	protected float getMouseAngle(int playerX, int playerY, int offSetX, int offSetY) {
		int xCenter = getPlayerCenter((int)playerX, offSetX);
		int yCenter = getPlayerCenter((int)playerY, offSetY);
		int xCursor = Gdx.input.getX();
		int yCursor = Math.abs(720 - Gdx.input.getY());
		int deltaX = xCursor - xCenter;
		int deltaY = yCenter - yCursor;
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
	
	protected Sprite rotateSprite(float rotation, Sprite player, int offSetX, int offSetY) {
		player.setOrigin(offSetX, offSetY);
		player.setRotation(rotation);
		return player;
	}
}

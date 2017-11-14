package com.speedball.game;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Utils {
	/**
	 * Begin utility functions
	 * TODO: Possibly move to separate utility class
	 */

	public Player createPlayerSprite(float initX, float initY) {
		return new Player(new Texture(Gdx.files.internal("player/playerNewSize.png")), initX, initY);
	}
	
	public Sprite createBackgroundSprite() {
		return new Sprite(new Texture(Gdx.files.internal("pbfield/grassBetter.png")));
		//return new Sprite(new Texture(Gdx.files.internal("pbfield/paintballFieldOne.png")));
	}
	/*
	 * Function that determines the player's x and y position.
	 * Player Inputs = WASD keys
	 */
	protected float[] movePlayer(float playerX, float playerY, float playerSpeed, float dt) {
		float[] array = new float[2];
//		float dt = Gdx.graphics.getDeltaTime();
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
	
	public boolean playerInBounds(float x, float y, float MAX_X, float MAX_Y) {
		if (x >= 0 && x <= MAX_X && y >= 0 && y <= MAX_Y) {
			return true;
		}
		return false;
	}
	
	// Function used to reposition player if out of bounds
	public float resetPlayerAtXBound(float x, float MAX_X) {
		if (x - MAX_X > 0) {
			return MAX_X;
		}
		else if (x <= 0) {
			return 0;
		}
		return x;
	}
  
	public float resetPlayerAtYBound(float y, float MAX_Y) {
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
	
	public float getMouseAngle(float playerX, float playerY, float offSetX, float offSetY, Camera camera) {
		float xCenter = getPlayerCenter(playerX, offSetX);
		float yCenter = getPlayerCenter(playerY, offSetY);
		float xCursor = Gdx.input.getX();
		Vector3 tmpCoords = new Vector3(xCursor,Gdx.input.getY(), 0);
        camera.unproject(tmpCoords);
		float deltaX = tmpCoords.x - xCenter;
		float deltaY = yCenter - tmpCoords.y;
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
	
//	protected boolean isPlayerSprinting() {
//		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
//			return true;
//		}
//		return false;
//	}
	protected float choosePlayerSpeed(float sprint, float walk, boolean sprinting) {
		if (sprinting) {
			return sprint;
		}
		return walk;
	}
	
	public Sprite rotateSprite(float angle, Sprite sprite, float offSetX, float offSetY, float playerX, float playerY) {
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
	
	protected float[] convertArrayList(ArrayList<Float> vertices) { 
		float[] floatArray = new float[vertices.size()];
		int i = 0;

		for (Float f : vertices) {
		    floatArray[i++] = (f != null ? f : Float.NaN);
		}
		return floatArray;
	}
	
	protected ArrayList<Float> convertArray(float[] array) { 
		ArrayList<Float> vertices = new ArrayList<Float>();
		for (int i = 0; i < array.length; i++) {
			vertices.add(array[i]);
		}
		return vertices;
	}
	
	/*
	 * returns the players custom hitbox
	 */
	protected float[] getPlayerVertices(Player player) {
		//float width = rectangle.getWidth() - Player.getPlayerCenterWidth();
		//float height = rectangle.getHeight() - Player.getPlayerCenterHeight();
		float width = 22.0f;
		float height = 20.0f;
		float x = player.getPlayerX() + 6.75f;
		float y = player.getPlayerY() + 9.20f;
		//float x = rectangle.getX();
		//float y = rectangle.getY();
		float[] retArray = {x, y, x + width, y, x + width, y + height, x, y + height};
		/*float[] retArray = {x + 10.687501f, y + 25.654848f, x + 8.437501f, y + 21.823f, x + 6.187501f, y + 19.283175f,
				x + 7.875001f, y + 13.619477f, x + 12.375002f, y + 12.9115f, x + 17.437502f, y + 15.74338f, x + 19.687502f, y + 19.283175f,
				x + 18.000002f, y + 21.238924f};*/
		return retArray;
	}
	
	protected void playerCollided(Player player, ArrayList<Bunker> bunkers) {
		float[] playerVertices = getPlayerVertices(player);
		Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
		for (Bunker bunker: bunkers) {
			if (bunker.isCollidable()) {
				float[] bunkerVertices = bunker.getVerticesArray();
				if (bunkerVertices.length != 0 && Intersector.overlapConvexPolygons(playerVertices, bunkerVertices, mtv)) {
				    //System.out.println("Normal: " + mtv.normal + " depth: " + mtv.depth);
					player.setCollided(true);
					//player.setMtv(mtv);
					//player.addBunkerCollidedWith(bunker);
					player.addMtv(mtv);
					
				}
//				else {
//					player.setCollided(false);
//				}
			}
		}
		//setResultantMtv(player, player.getMtvAtCollidedBunkers());
	}
	
	/*protected void setResultantMtv(Player player, ArrayList<Intersector.MinimumTranslationVector> mtvAtCollidedBunkers) {
		
	}*/
	
	/*
	 * Uses the minimum translation vector to correct the players position
	 * if the player starts to collide with an polygon
	 */
	public void playerMtvLogic(Player player, PaintballMap pbMap) {
		playerCollided(player, pbMap.getBunkers());
        if (player.getCollided() == false) {
            player.setBounds(player.getPlayerX(), player.getPlayerY(), Player.getPlayerWidth(), Player.getPlayerHeight());
        }
        else {
        	float newPlayerX = player.getPlayerX();
        	float newPlayerY = player.getPlayerY();
        	for (int i = 0; i < player.getMtvAtCollidedBunkers().size(); i++) {
//        		if (player.getMtvAtCollidedBunkers().size() > 1) {
//        			//System.out.println("MTV Normal: " + player.getMtvAtCollidedBunkers().get(i).normal + " MTV Depth: " + player.getMtvAtCollidedBunkers().get(i).depth);
//        		}
        		newPlayerX += player.getMtvAtCollidedBunkers().get(i).normal.x * player.getMtvAtCollidedBunkers().get(i).depth;
        		newPlayerY += player.getMtvAtCollidedBunkers().get(i).normal.y * player.getMtvAtCollidedBunkers().get(i).depth;
        		
        	}
        	 
            //float newPlayerX = player.getPlayerX() + (player.getNormalVector().x * player.getDepth());
            //float newPlayerY = player.getPlayerY() + (player.getNormalVector().y * player.getDepth());
        	player.setMtvAtCollidedBunkers(new ArrayList<Intersector.MinimumTranslationVector>());
        	player.setBounds(newPlayerX, newPlayerY, Player.getPlayerWidth(), Player.getPlayerHeight());
            player.setPlayerX(newPlayerX);
            player.setPlayerY(newPlayerY);
        	player.setCollided(false);
        }
	}
	
	public void checkAndMovePlayer(float x, float y, float maxX, float maxY, Player player, boolean sprinting, float dt) {
		player.setPlayerSpeed(choosePlayerSpeed(Player.getSprintSpeed(), Player.getWalkSpeed(), sprinting));
		if (playerInBounds(x, y, maxX, maxY)) {
			float[] playerXY = movePlayer(x, y, player.getPlayerSpeed(), dt);
			player.setPlayerX(playerXY[0]);
			player.setPlayerY(playerXY[1]);
		}
		else {
			player.setPlayerX(resetPlayerAtXBound(x, maxX));
			player.setPlayerY(resetPlayerAtYBound(y, maxY));
		}
	}
}

package com.speedball.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
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
	private static final int MAX_X = 1000;
	private static final int MAX_Y = 645;
	SpriteBatch batch;
	Texture img;
	float playerSpeed = 100.0f;
	private Sprite player;
	private Sprite background;
	float playerX;
	float playerY;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		createPlayerSprite();
		createBackgroundSprite();
		playerX = 0;
		playerY = 0;
	}

	@Override
	// Player moves faster when moving diagonally
	public void render () {
		System.out.println(playerInBounds((int)playerX, (int)playerY));
		
		checkValidPlayer();
		
	    Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		System.out.println("playerX" + playerX);
		System.out.println("playerY" + playerX);
		
		batch.begin();
		batch.draw(background, 0, 0, 1080, 720);
		batch.draw(player, (int)playerX, (int)playerY, 80, 53);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	/**
	 * Begin utility functions
	 * TODO: Possibly move to separate utility class
	 */
	private void createPlayerSprite() {
		FileHandle playerFileHandle = Gdx.files.internal("shotgun/idle/survivor-idle_shotgun_0.png");
		Texture playerTexture = new Texture(playerFileHandle);
		player = new Sprite(playerTexture);
	}
	private void createBackgroundSprite() {
		FileHandle backgroundFileHandle = Gdx.files.internal("grass.png");
		Texture backgroundTexture = new Texture(backgroundFileHandle);
		background = new Sprite(backgroundTexture);
	}
	/*
	 * Function that determines the player's x and y position.
	 * Player Inputs = WASD keys
	 */
	public void movePlayer() {
		if (Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.W)) {
			playerX -= Gdx.graphics.getDeltaTime() * playerSpeed/2;
			playerY += Gdx.graphics.getDeltaTime() * playerSpeed/2;
		}
		else if (Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.W)) {
			playerX += Gdx.graphics.getDeltaTime() * playerSpeed/2;
			playerY += Gdx.graphics.getDeltaTime() * playerSpeed/2;
		}
		else if (Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.S)) {
			playerX -= Gdx.graphics.getDeltaTime() * playerSpeed/2;
			playerY -= Gdx.graphics.getDeltaTime() * playerSpeed/2;
		}
		else if (Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.S)) {
			playerX += Gdx.graphics.getDeltaTime() * playerSpeed/2;
			playerY -= Gdx.graphics.getDeltaTime() * playerSpeed/2;
		}
		else if(Gdx.input.isKeyPressed(Keys.A)) {
			playerX -= Gdx.graphics.getDeltaTime() * playerSpeed;
		}
		else if(Gdx.input.isKeyPressed(Keys.D)) {
	    		playerX += Gdx.graphics.getDeltaTime() * playerSpeed;
		}
		else if(Gdx.input.isKeyPressed(Keys.W)) {
	    		playerY += Gdx.graphics.getDeltaTime() * playerSpeed;
		}
		else if(Gdx.input.isKeyPressed(Keys.S)) {
	    		playerY -= Gdx.graphics.getDeltaTime() * playerSpeed;
		}
	}
	private boolean playerInBounds(int x, int y) {
		if (x >= 0 && x <= MAX_X && y >= 0 && y <= MAX_Y) {
			return true;
		}
		return false;
	}
	// Function used to reposition player if out of bounds
	private void resetPlayerAtBound(int x, int y) {
		if (x - MAX_X > 0 && y - MAX_Y > 0) {
			playerX = MAX_X;
			playerY = MAX_Y;
		}
		else if (x <= 0 && y - MAX_Y > 0) {
			playerX = 0;
			playerY = MAX_Y;
		}
		else if (x - MAX_X > 0 && y <= 0) {
			playerX = MAX_X;
			playerY = 0;
		}
		else if (x <= 0 && y <= 0) {
			playerX = 0;
			playerY = 0;
		}
		else if (x - MAX_X > 0) {
			playerX = MAX_X;
		}
		else if (x <= 0) {
			playerX = 0;
		}
		else if (y - MAX_Y > 0) {
			playerY = MAX_Y;
		}
		else if (y <= 0) {
			playerY = 0;
		}
	}
	private void checkValidPlayer() {
		if (playerInBounds((int)playerX, (int)playerY)) {
			movePlayer();
		}
		else {
			resetPlayerAtBound((int)playerX,(int)playerY);
		}
	}
}

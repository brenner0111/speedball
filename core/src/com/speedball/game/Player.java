package com.speedball.game;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
/**
 * Wrapper class that adds a collided flag to the sprite object.
 * @author Calvin Yarboro
 *
 */
public class Player extends Sprite  {
	private static final float PLAYER_WIDTH = 36.0f;
	private static final float PLAYER_HEIGHT = 26.28f;
	private static final float PLAYER_CENTER_WIDTH = PLAYER_WIDTH / 2;
	private static final float PLAYER_CENTER_HEIGHT = PLAYER_HEIGHT / 2;
	private static final float PLAYER_GUN_HEIGHT = PLAYER_CENTER_HEIGHT - 2;
	private static final float PLAYER_GUN_WIDTH = 34;
	private static final float WALK_SPEED = 150.0f;
	private static final float SPRINT_SPEED = 200.0f;
	private static final float GUN_RADIUS = (float)Math.sqrt(Math.pow(PLAYER_GUN_WIDTH - PLAYER_CENTER_WIDTH, 2) + Math.pow(PLAYER_CENTER_HEIGHT - PLAYER_GUN_HEIGHT, 2));
	
	private float initX = 0.0f;
	private float initY = 0.0f;
	private boolean collided;
	private float playerSpeed;
	private float playerX;
	private float playerY;
	private float gunX;
	private float gunY;
	private int paintballCounter;
	private ArrayList<Paintball> paintballs;
	

	public Player(Texture backgroundTexture, float initX, float initY) {
		super(backgroundTexture);
		this.collided = false;
		this.initX = initX;
		this.initY = initY;
	}
	public boolean getCollided() {
		return this.collided;
	}
	public void setCollided(boolean value) {
		this.collided = value;
	}
	protected static float getPlayerWidth() {
		return PLAYER_WIDTH;
	}
	protected static float getPlayerHeight() {
		return PLAYER_HEIGHT;
	}
	protected static float getPlayerCenterWidth() {
		return PLAYER_CENTER_WIDTH;
	}
	protected static float getPlayerCenterHeight() {
		return PLAYER_CENTER_HEIGHT;
	}
	protected static float getPlayerGunHeight() {
		return PLAYER_GUN_HEIGHT;
	}
	protected static float getPlayerGunWidth() {
		return PLAYER_GUN_WIDTH;
	}
	protected static float getWalkSpeed() {
		return WALK_SPEED;
	}
	protected static float getSprintSpeed() {
		return SPRINT_SPEED;
	}
	protected float getInitX() {
		return initX;
	}
	protected float getInitY() {
		return initY;
	}
	public static float getGunRadius() {
		return GUN_RADIUS;
	}
	public float getPlayerSpeed() {
		return playerSpeed;
	}
	public void setPlayerSpeed(float playerSpeed) {
		this.playerSpeed = playerSpeed;
	}
	protected float getPlayerX() {
		return playerX;
	}
	protected void setPlayerX(float playerX) {
		this.playerX = playerX;
	}
	protected float getPlayerY() {
		return playerY;
	}
	protected void setPlayerY(float playerY) {
		this.playerY = playerY;
	}
	protected float getGunX() {
		return gunX;
	}
	protected void setGunX(float gunX) {
		this.gunX = gunX;
	}
	protected float getGunY() {
		return gunY;
	}
	protected void setGunY(float gunY) {
		this.gunY = gunY;
	}
	protected void setInitX(float initX) {
		this.initX = initX;
	}
	protected void setInitY(float initY) {
		this.initY = initY;
	}
	protected int getPaintballCounter() {
		return paintballCounter;
	}
	protected void setPaintballCounter(int paintballCounter) {
		this.paintballCounter = paintballCounter;
	}
	protected ArrayList<Paintball> getPaintballs() {
		return paintballs;
	}
	protected void setPaintballs(ArrayList<Paintball> paintballs) {
		this.paintballs = paintballs;
	}
}


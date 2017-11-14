package com.speedball.game;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.speedball.server.SpeedballServer;
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
	private boolean isHit;
	private float playerSpeed;
	private float playerX;
	private float playerY;
	private float gunX;
	private float gunY;
	private int paintballCounter;
	private float mouseAngle;
	private ArrayList<Paintball> paintballs;
	private Intersector.MinimumTranslationVector mtv;
	private ArrayList<Bunker> bunkersCollidedWith;
	private ArrayList<Intersector.MinimumTranslationVector> mtvAtCollidedBunkers;
	
	

	public Player(Texture backgroundTexture, float initX, float initY) {
		super(backgroundTexture);
		this.collided = false;
		this.isHit = false;
		this.initX = initX;
		this.initY = initY;
		this.playerX = this.initX;
		this.playerY = this.initY;
		this.gunX = PLAYER_GUN_WIDTH;
		this.gunY = PLAYER_GUN_HEIGHT;
		this.paintballCounter = -1;
		this.mouseAngle = 0f;
		this.playerSpeed = WALK_SPEED;
		paintballs = new ArrayList<Paintball>();
		setMtv(new Intersector.MinimumTranslationVector());
		bunkersCollidedWith = new ArrayList<Bunker>();
		mtvAtCollidedBunkers = new ArrayList<Intersector.MinimumTranslationVector>();
	}
	public Player(float initX, float initY) {
		this.collided = false;
		this.isHit = false;
		this.initX = initX;
		this.initY = initY;
		this.playerX = this.initX;
		this.playerY = this.initY;
		this.gunX = PLAYER_GUN_WIDTH;
		this.gunY = PLAYER_GUN_HEIGHT;
		this.paintballCounter = -1;
		this.mouseAngle = 0f;
		this.playerSpeed = WALK_SPEED;
		paintballs = new ArrayList<Paintball>();
		setMtv(new Intersector.MinimumTranslationVector());
		bunkersCollidedWith = new ArrayList<Bunker>();
		mtvAtCollidedBunkers = new ArrayList<Intersector.MinimumTranslationVector>();
	}
	public boolean getCollided() {
		return this.collided;
	}
	public void setCollided(boolean value) {
		this.collided = value;
	}
	public boolean isHit() {
		return isHit;
	}
	protected void setHit(boolean isHit) {
		this.isHit = isHit;
	}
	public static float getPlayerWidth() {
		return PLAYER_WIDTH;
	}
	public static float getPlayerHeight() {
		return PLAYER_HEIGHT;
	}
	public static float getPlayerCenterWidth() {
		return PLAYER_CENTER_WIDTH;
	}
	public static float getPlayerCenterHeight() {
		return PLAYER_CENTER_HEIGHT;
	}
	public static float getPlayerGunHeight() {
		return PLAYER_GUN_HEIGHT;
	}
	public static float getPlayerGunWidth() {
		return PLAYER_GUN_WIDTH;
	}
	protected static float getWalkSpeed() {
		return WALK_SPEED;
	}
	protected static float getSprintSpeed() {
		return SPRINT_SPEED;
	}
	public float getInitX() {
		return initX;
	}
	public float getInitY() {
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
	public float getPlayerX() {
		return playerX;
	}
	public void setPlayerX(float playerX) {
		this.playerX = playerX;
	}
	public float getPlayerY() {
		return playerY;
	}
	public void setPlayerY(float playerY) {
		this.playerY = playerY;
	}
	public float getGunX() {
		return gunX;
	}
	public void setGunX(float gunX) {
		this.gunX = gunX;
	}
	public float getGunY() {
		return gunY;
	}
	public void setGunY(float gunY) {
		this.gunY = gunY;
	}
	protected void setInitX(float initX) {
		this.initX = initX;
	}
	protected void setInitY(float initY) {
		this.initY = initY;
	}
	public int getPaintballCounter() {
		return paintballCounter;
	}
	public void setPaintballCounter(int paintballCounter) {
		this.paintballCounter = paintballCounter;
	}
	public void incrementPaintballCounter() {
	    this.paintballCounter++;
	}
	public ArrayList<Paintball> getPaintballs() {
		return paintballs;
	}
	public void addPaintball(Paintball paintball) {
	    paintballs.add(paintball);
	}
	protected void setPaintballs(ArrayList<Paintball> paintballs) {
		this.paintballs = paintballs;
	}
    public float getMouseAngle(){
        return mouseAngle;
    }
    public void setMouseAngle(float mouseAngle){
        this.mouseAngle = mouseAngle;
    }
    public Intersector.MinimumTranslationVector getMtv(){
        return mtv;
    }
    public void setMtv(Intersector.MinimumTranslationVector mtv){
        this.mtv = mtv;
    }
    public Vector2 getNormalVector() {
        return mtv.normal;
    }
    public float getDepth() {
        return mtv.depth;
    }

	public ArrayList<Bunker> getBunkersCollidedWith() {
		return bunkersCollidedWith;
	}
	public void addBunkerCollidedWith(Bunker bunker) {
		bunkersCollidedWith.add(bunker);
	}
	public ArrayList<Intersector.MinimumTranslationVector> getMtvAtCollidedBunkers(){
		return mtvAtCollidedBunkers;
	}
	public void addMtv(Intersector.MinimumTranslationVector vector) {
		mtvAtCollidedBunkers.add(vector);
	}
	public void setMtvAtCollidedBunkers(ArrayList<Intersector.MinimumTranslationVector> vector) {
		mtvAtCollidedBunkers = vector;
	}
	

}


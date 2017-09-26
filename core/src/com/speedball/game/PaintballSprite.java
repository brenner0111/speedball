package com.speedball.game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
/**
 * Wrapper class that adds a collided flag to the sprite object.
 * @author Calvin Yarboro
 *
 */
public class PaintballSprite extends Sprite  {
	private boolean collided;
	private float slope;
	private int quadrant;
//	private float rotatedX;
//	private float rotatedY;
	public PaintballSprite(Texture backgroundTexture, float slope, int quadrant) {
		super(backgroundTexture);
		this.slope = slope;
		this.collided = false;
		this.setQuadrant(quadrant);
//		this.rotatedX = 0.0f;
//		this.rotatedY = 0.0f;
	}
	public boolean getCollided() {
		return this.collided;
	}
	public void setCollided(boolean value) {
		this.collided = value;
	}
	public float getSlope() {
		return slope;
	}
	public void setSlope(float slope) {
		this.slope = slope;
	}
	public int getQuadrant() {
		return quadrant;
	}
	public void setQuadrant(int quadrant) {
		this.quadrant = quadrant;
	}
//	public float getRotatedY() {
//		return rotatedY;
//	}
//	public void setRotatedY(float rotatedY) {
//		this.rotatedY = rotatedY;
//	}
//	public float getRotatedX() {
//		return rotatedX;
//	}
//	public void setRotatedX(float rotatedX) {
//		this.rotatedX = rotatedX;
//	}
}

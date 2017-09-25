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
	public PaintballSprite(Texture backgroundTexture, float slope) {
		super(backgroundTexture);
		this.setSlope(slope);
		this.collided = false;
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
}

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
	public PaintballSprite(Texture backgroundTexture) {
		super(backgroundTexture);
		this.collided = false;
	}
	public boolean getCollided() {
		return this.collided;
	}
	public void setCollided(boolean value) {
		this.collided = value;
	}
}

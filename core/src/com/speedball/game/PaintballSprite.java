package com.speedball.game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	public void setOrigin(float originX, float originY) {
		super.setOrigin(originX, originY);
	}
	public void setRotation(float degrees) {
		super.setRotation(degrees);
	}
	public void setBounds(float x, float y, float width, float height) {
		super.setBounds(x, y, width, height);
	}
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
}

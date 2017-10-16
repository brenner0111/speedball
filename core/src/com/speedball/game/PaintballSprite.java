package com.speedball.game;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

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
	private ArrayList<Float> vertices = new ArrayList<Float>(); //counter-clockwise list of vertices on bunker;
	private SpriteUtils spriteUtils;

	public PaintballSprite(Texture backgroundTexture, float slope, int quadrant) {
		super(backgroundTexture);
		this.slope = slope;
		this.collided = false;
		this.setQuadrant(quadrant);
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
	public float[] getVerticesArray() {
		return spriteUtils.convertArrayList(vertices);
	}
	public void setVertices(ArrayList<Float> vertices) {
		this.vertices = vertices;
	}

}

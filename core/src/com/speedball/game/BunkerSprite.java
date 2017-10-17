package com.speedball.game;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
/**
 * Wrapper class that adds a collided flag to the sprite object and arraylist of vertices for collisions.
 * @author Calvin Yarboro
 *
 */
public class BunkerSprite extends Sprite  {
	private boolean collided;
	private ArrayList<Float> vertices = new ArrayList<Float>(); //counter-clockwise list of vertices on bunker;
	private Utils utils = new Utils();

	public BunkerSprite(Texture backgroundTexture, float[] vertices) {
		super(backgroundTexture);
		this.setVertices(utils.convertArray(vertices));
		this.collided = false;
	}
	public boolean getCollided() {
		return this.collided;
	}
	public void setCollided(boolean value) {
		this.collided = value;
	}
	public float[] getVerticesArray() {
		return utils.convertArrayList(vertices);
	}
	public void setVertices(ArrayList<Float> vertices) {
		this.vertices = vertices;
	}


}
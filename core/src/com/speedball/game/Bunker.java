package com.speedball.game;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
/**
 * Wrapper class that adds a collided flag to the sprite object and arraylist of vertices for collisions.
 * @author Calvin Yarboro
 *
 */
public class Bunker extends Sprite  {
	private ArrayList<Float> vertices = new ArrayList<Float>(); //counter-clockwise list of vertices on bunker;
	private Utils utils = new Utils(); //Try to remove? Each bunker gets a Utils object
	private boolean collidable;
	private boolean drawable;

	/**
	 * Constructor for Bunkers with Textures
	 * 
	 */
	public Bunker(Texture backgroundTexture, float[] vertices, boolean collidable) {
		super(backgroundTexture);
		this.setVertices(utils.convertArray(vertices));
		this.setCollidable(collidable);
		this.drawable = true;
	}
	
	/**
	 * Constructor for Bunkers without textures
	 * This can be used for breaking up the existing bunkers
	 * into multiple invisible collidable Pologons
	 */
	public Bunker(float[] vertices) {
	    this.setVertices(utils.convertArray(vertices));
	    this.setCollidable(collidable);
	    this.drawable = false;
	    this.collidable = true;
	}
	public float[] getVerticesArray() {
		return utils.convertArrayList(vertices);
	}
	public void setVertices(ArrayList<Float> vertices) {
		this.vertices = vertices;
	}
	public boolean isCollidable() {
		return collidable;
	}
	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}

    public boolean isDrawable(){
        return drawable;
    }

}
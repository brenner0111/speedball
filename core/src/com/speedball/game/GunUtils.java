package com.speedball.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GunUtils {

	private static final float PBALL_DIST_CAP = 1.0f;
	Utils utils = new Utils();

	public Paintball createPaintballSprite(float x, float y, float slope, int quadrant) {
		FileHandle paintBallHandle = Gdx.files.internal("paintballs/redPaintball.png");
		Texture backgroundTexture = new Texture(paintBallHandle);
		return new Paintball(backgroundTexture, slope, quadrant);

	}
	public float getPlayerGunCoord(float coordinate, float offSet, boolean isX) {
		//return coordinate of the tip of the gun barrel
		if (isX) {
			return coordinate + offSet;
		}
		else {
			return coordinate + offSet;
		}
	}

	public int drawPaintballs(SpriteBatch b, ArrayList<Paintball> paintballs, int paintballCounter, ArrayList<Bunker> bunkers, ArrayList<Player> players) {
		for (Iterator<Paintball> iterator = paintballs.iterator(); iterator.hasNext();) {
			Paintball paintball = iterator.next();

			if (!paintball.getCollided()) {
				paintballCollided(paintball, bunkers, paintball.getQuadrant(), players);
			}

			if (paintballInWindow(paintball)) {
				paintball.draw(b);
			}
			else {
				iterator.remove();
				paintballCounter = paintballCounter - 1;
			}
		}
		return paintballCounter;
	}

	public String addPlayerPaintballs(ArrayList<Paintball> paintballs, ArrayList<Bunker> bunkers, ArrayList<Player> players, String clientString, int i) {
		for (int j = 0; j < paintballs.size(); j++) {
			if ((paintballs.get(j).getX() > 0 && paintballs.get(j).getX() < 1045)
					&& (paintballs.get(j).getY() > 0 && paintballs.get(j).getY() < 690)) {
				if (!paintballCollided(paintballs.get(j), bunkers, paintballs.get(j).getQuadrant(), players)) {
					clientString += "pb" + (i + 1) + " ";
					clientString += paintballs.get(j).getSlope() + " ";
					clientString += paintballs.get(j).getQuadrant() + " ";
					clientString += paintballs.get(j).getX() + " ";
					clientString += paintballs.get(j).getY() + " ";
				}
				else {
					clientString += "pb" + (i + 1) + " ";
					clientString += paintballs.get(j).getSlope() + " ";
					clientString += paintballs.get(j).getQuadrant() + " ";
					clientString += paintballs.get(j).getX() + " ";
					clientString += paintballs.get(j).getY() + " ";
				}
				
			}
		}
		return clientString;
	}
	
	
	public int drawPaintballsClient(SpriteBatch b, ArrayList<Paintball> paintballs, int paintballCounter) {
		for (Iterator<Paintball> iterator = paintballs.iterator(); iterator.hasNext();) {
			Paintball paintball = iterator.next();         
			paintball.draw(b);
		}
		return paintballCounter;
	}

	/**
	 * OPTODO: add paintball slowdown overtime
	 * @param paintball
	 * @param paintballSpeed
	 */
	public void updatePaintballXY(Paintball paintball, int quadrant) {
		float slope = Math.abs(paintball.getSlope());
		float r = (float)Math.sqrt(1 + Math.pow(slope, 2));
		float x = 0.0f;
		float y = 0.0f;

		if (quadrant == 1) {
			x = paintball.getX() + (PBALL_DIST_CAP/r);
			y = paintball.getY() + ((PBALL_DIST_CAP*slope)/r);
			paintball.setX(x);
			paintball.setY(y);
		}
		else if (quadrant == 2) {
			x = paintball.getX() - (PBALL_DIST_CAP/r);
			y = paintball.getY() + ((PBALL_DIST_CAP*slope)/r);
			paintball.setX(x);
			paintball.setY(y);
		}
		else if (quadrant == 3) {
			x = paintball.getX() - (PBALL_DIST_CAP/r);
			y = paintball.getY() - ((PBALL_DIST_CAP*slope)/r);
			paintball.setX(x);
			paintball.setY(y);
		}
		else if (quadrant == 4){
			x = paintball.getX() + (PBALL_DIST_CAP/r);
			y = paintball.getY() - ((PBALL_DIST_CAP*slope)/r);
			paintball.setX(x);
			paintball.setY(y);
		}
		else if (quadrant == 0) {
			x = paintball.getX() + (PBALL_DIST_CAP/r);
			paintball.setX(x);
		}
		else if (quadrant == 90) {
			y = paintball.getY() + ((PBALL_DIST_CAP*slope)/r);
			paintball.setY(y);
		}
		else if (quadrant == 180) {
			x = paintball.getX() - (PBALL_DIST_CAP/r);
			paintball.setX(x);
		}
		else if (quadrant == 270) {
			y = paintball.getY() - ((PBALL_DIST_CAP*slope)/r);
			paintball.setY(y);
		}
		//System.out.println("Paintball X: " + paintball.getX() + " Paintball Y: " + paintball.getY());
	}

	public int checkQuadrant(float angle) {
		if (angle > 0.0f && angle < 90.0f) {
			return 1;
		}
		else if (angle > 90.f && angle < 180.0f) {
			return 2;
		}
		else if (angle > 180.0f && angle < 270.0f) {
			return 3;
		}
		else if (angle > 270.0f && angle < 360.0f) {
			return 4;
		}
		else if (angle == 0.0f || angle == 360.0f) {
			return 0;
		}
		else if (angle == 90.0f) {
			return 90;
		}
		else if (angle == 180.0f) {
			return 180;
		}
		else if (angle == 270.0f) {
			return 270;
		}
		else {
			return -1;
		} 
	}

	public float[] updateRealGunXY(float angle, float centerX, float centerY, float gunRadius) {
		float[] coords = new float[2];
		coords[0] = centerX + (float)(gunRadius * Math.cos(Math.toRadians(angle)));
		coords[1] = centerY + (float)(gunRadius * Math.sin(Math.toRadians(angle)));
		return coords;
	}

	public float[] getPaintballVertices(Paintball paintball) {
		Rectangle rectangle = paintball.getBoundingRectangle();
		float width = rectangle.getWidth();
		float height = rectangle.getHeight();
		float x = rectangle.getX();
		float y = rectangle.getY();
		float[] retArray = {x, y, x + width, y, x + width, y + height, x, y + height};
		return retArray;
	}

	public boolean paintballInWindow(Paintball paintball) {
		float x = paintball.getBoundingRectangle().getX();
		float y = paintball.getBoundingRectangle().getY();
		if (x > 1045 || x < 0) {
			return false;
		}
		else if (y > 690 || y < 0) {
			return false;
		}
		return true;
	}

	public boolean paintballCollided(Paintball paintball, ArrayList<Bunker> bunkers, int quadrant, ArrayList<Player> players) {
		float[] paintballVertices = getPaintballVertices(paintball);

		for (int i = 0; i < 25; i++) {

			for (Bunker bunker : bunkers) {
				if (bunker.isCollidable()) {
					float[] bunkerVertices = bunker.getVerticesArray();
					if (bunkerVertices.length != 0 && Intersector.overlapConvexPolygons(paintballVertices, bunkerVertices, null)) {
						paintball.setCollided(true);
						return true;
					}
					else {
						paintball.setCollided(false);
					}
				}
			}
			
			for (Player player : players) {
				float[] playerVert = utils.getPlayerVertices(player);
				//System.out.println("Player Vert: " + playerVert[0] + " " + playerVert[1] + " " + playerVert[2] + " " + playerVert[3]);
				if (playerVert.length != 0 && Intersector.overlapConvexPolygons(paintballVertices, playerVert, null)) {
					paintball.setCollided(true);
					//System.out.println("HIT");
					player.setHit(true);
					return true;
				}
				else {
					paintball.setCollided(false);
					player.setHit(false);
				}
			}
			updatePaintballXY(paintball, quadrant);
			paintballVertices = getPaintballVertices(paintball);
		} 
		return false; 
	}

	public boolean checkAndFireGun(float angle, Player player, Camera camera) {
		// If screen was just touched or left click was pushed on desktop
		// Gdx.input.isButtonPressed(Input.Buttons.LEFT)
		//Gdx.input.justTouched()
		if (Gdx.input.justTouched()) {
			//sets gun position before rotation
			player.setGunX(getPlayerGunCoord(player.getPlayerX(), Player.getPlayerGunWidth(), true)); 
			player.setGunY(getPlayerGunCoord(player.getPlayerY(), Player.getPlayerGunHeight(), false));
			//Get the current center of the player
			float centerX = player.getPlayerX() + Player.getPlayerCenterWidth();
			float centerY = player.getPlayerY() + Player.getPlayerCenterHeight();
			//Get the array of gun coordinates based on the angle, center, and radius
			float[] coords = updateRealGunXY(angle, centerX, centerY, Player.getGunRadius());
			//Get the current mouse coordinates
			float mouseX = Gdx.input.getX();
			Vector3 tmpCoords = new Vector3(mouseX,Gdx.input.getY(), 0);
			camera.unproject(tmpCoords);
			//Get the slope of between the gun coordinates and mouse coordinates 
			float slope = (tmpCoords.y - coords[1]) / (tmpCoords.x - coords[0]);
			//Find what quadrant our mouse is in based on the angle
			int quadrant = checkQuadrant(angle);
			//Add the paintball into the ArrayList with it's current state
			player.addPaintball(createPaintballSprite(player.getGunX(), player.getGunY(), slope, quadrant));

			System.out.println("ANGLE: " + angle + " SLOPE: " + slope + " realgunX: " + coords[0] + " realgunY: " + coords[1] + 
					" fakeGunX: "+ player.getGunX() + " fakeGunY: " + player.getGunY() + " playerX: " + player.getPlayerX() + " playerY:" + player.getPlayerY() + " Quadrant: " + quadrant +
					" MouseXY: (" + Gdx.input.getX() + ", " + Gdx.input.getY() + ")" + "MappedMouseXY: (" + tmpCoords.x + "f, " + tmpCoords.y + "f,)" );
			player.incrementPaintballCounter();
			return true;
		}
		return false;
	}
}
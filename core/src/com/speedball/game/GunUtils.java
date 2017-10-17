package com.speedball.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class GunUtils {
    
    private static final float PBALL_DIST_CAP = 25.0f;

	protected PaintballSprite createPaintballSprite(float x, float y, float slope, int quadrant) {
	    FileHandle paintBallHandle = Gdx.files.internal("paintballs/redPaintball.png");
	    Texture backgroundTexture = new Texture(paintBallHandle);
	    return new PaintballSprite(backgroundTexture, slope, quadrant);
	    
	}
	protected float getPlayerGunCoord(float coordinate, float offSet, boolean isX) {
	    //return coordinate of the tip of the gun barrel
	    if (isX) {
	        return coordinate + offSet;
	    }
	    else {
	        return coordinate + offSet;
	    }
	}
	
	protected int drawPaintballs(SpriteBatch b, ArrayList<PaintballSprite> paintballs, float paintballSpeed, int paintballCounter, ArrayList<BunkerSprite> bunkers) {
		for (Iterator<PaintballSprite> iterator = paintballs.iterator(); iterator.hasNext();) {
			PaintballSprite paintball = iterator.next();
			if (paintball.getCollided() == false) {
				updatePaintballXY(paintball, paintballSpeed, paintball.getQuadrant());
			}
			System.out.println(Arrays.toString(getPaintballVertices(paintball)));
			paintballCollided(paintball, bunkers);
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
	/**
	 * OPTODO: add paintball slowdown overtime
	 * @param paintball
	 * @param paintballSpeed
	 */
	protected void updatePaintballXY(PaintballSprite paintball, float paintballSpeed, int quadrant) {
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
		else {
			
		}
	}

	protected int checkQuadrant(float angle) {
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
	
	protected float[] updateRealGunXY(float angle, float centerX, float centerY, float gunRadius) {
	    float[] coords = new float[2];
	    coords[0] = centerX + (float)(gunRadius * Math.cos(Math.toRadians(angle)));
        coords[1] = centerY + (float)(gunRadius * Math.sin(Math.toRadians(angle)));
	    return coords;
	}

	protected float[] getPaintballVertices(PaintballSprite paintball) {
		Rectangle rectangle = paintball.getBoundingRectangle();
		float width = rectangle.getWidth();
		float height = rectangle.getHeight();
		float x = rectangle.getX();
		float y = rectangle.getY();
		float[] retArray = {x, y, x + width, y, x + width, y + height, x, y + height};
		return retArray;
	}
	protected boolean paintballInWindow(PaintballSprite paintball) {
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
	protected void paintballCollided(PaintballSprite paintball, ArrayList<BunkerSprite> bunkers) {
		float[] paintballVertices = getPaintballVertices(paintball);
		for (BunkerSprite bunker: bunkers) {
			float[] bunkerVertices = bunker.getVerticesArray();
			if (bunkerVertices.length != 0 && Intersector.overlapConvexPolygons(paintballVertices, bunkerVertices, null)) {
				paintball.setCollided(true);
				break;
			}
			else {
				paintball.setCollided(false);
			}
		}
	}
}

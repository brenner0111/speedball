package com.speedball.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GunUtils {
    
    private static final float PBALL_DIST_CAP = 25.0f;

	protected PaintballSprite createPaintballSprite(float x, float y, float slope, int quadrant) {
	    FileHandle paintBallHandle = Gdx.files.internal("redPaintball.png");
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
	
	protected void drawPaintballs(SpriteBatch b, ArrayList<PaintballSprite> paintballs, float paintballSpeed) {
		for (PaintballSprite paintball : paintballs) {
			updatePaintballXY(paintball, paintballSpeed, paintball.getQuadrant());
			paintball.draw(b);
		}
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

}

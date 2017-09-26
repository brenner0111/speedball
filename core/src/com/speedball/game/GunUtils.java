package com.speedball.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GunUtils {
    
    private static final float PBALL_DIST_CAP = 10.0f;

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
		else {
		    x = paintball.getX() + (PBALL_DIST_CAP/r);
		    y = paintball.getY() - ((PBALL_DIST_CAP*slope)/r);
		    paintball.setX(x);
            paintball.setY(y);
		}
	}
	
	protected int checkQuadrant(float gunX, float gunY, float mouseX, float mouseY) {
		if (mouseX < gunX && mouseY < gunY) {
			return 3;
		}
		else if (mouseX < gunX && mouseY > gunY) {
			return 2;
		}
		else if (mouseX > gunX && mouseY < gunY) {
			return 4;
		}
		return 1;
	}
}

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
	
//	protected int checkQuadrant(float gunX, float gunY, float mouseX, float mouseY) {
//		if (mouseX < gunX && mouseY < gunY) {
//			return 3;
//		}
//		else if (mouseX < gunX && mouseY > gunY) {
//			return 2;
//		}
//		else if (mouseX > gunX && mouseY < gunY) {
//			return 4;
//		}
//		return 1;
//	}
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
	protected float getMouseAngle(float gunX, float gunY) {
		int xCursor = Gdx.input.getX();
		int yCursor = Math.abs(720 - Gdx.input.getY());
		float deltaX = xCursor - gunX;
		float deltaY = gunY - yCursor;
		double theta_radians = Math.atan2(deltaY, deltaX);
		double degrees = Math.toDegrees(theta_radians);
		if (degrees < 0) {
			return (float)Math.abs(degrees);
		}
		else if (degrees > 0) {
			return (float)(360 - degrees);
		}
		
		return (float)degrees;
	} 
//	protected float getSlope(float gunX, float gunY, float mouseX, float mouseY) {
//		 float slope = (mouseY - gunY) / (mouseX - gunX);
//		 return slope;
//	}
//	protected PaintballSprite rotatePaintball(float rotation, PaintballSprite sprite, float offSetX, float offSetY, float gunX, float gunY, float originX, float originY) {
//		sprite.setOrigin(offSetX, offSetY);
//		float newGunX = calcGunX(gunX, gunY, rotation, originX, originY);
//		float newGunY = calcGunY(gunX, gunY, rotation, originX, originY);
//		sprite.setRotatedX(newGunX);
//		sprite.setRotatedY(newGunY);
//		sprite.setRotation(rotation);
//		return sprite;
//	}
//	protected float calcGunX(float gunX, float gunY, float rotation, float originX, float originY) {
//		float newGunX = ((float)Math.cos(Math.toRadians(rotation)) * (gunX- originX) - ((float)Math.sin(Math.toRadians(rotation)) * (gunY - originY)));
//		newGunX = newGunX + originX;
//		return newGunX;
//	}
//	protected float calcGunY(float gunX, float gunY, float rotation, float originX, float originY) {
//		float newGunY = ((float)Math.sin(Math.toRadians(rotation)) * (gunX- originX) + ((float)Math.cos(Math.toRadians(rotation)) * (gunY - originY)));
//		newGunY = newGunY + originY;
//		return newGunY;
//	}
}

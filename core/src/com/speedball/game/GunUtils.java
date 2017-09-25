package com.speedball.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GunUtils {

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
		if (quadrant == 1) {
			paintball.setX(paintball.getX() + paintballSpeed);
			paintball.setY(paintball.getY() + paintballSpeed * slope);
		}
		else if (quadrant == 2) {
			paintball.setX(paintball.getX() - paintballSpeed);
			paintball.setY(paintball.getY() + paintballSpeed * slope);
		}
		else if (quadrant == 3) {
			paintball.setX(paintball.getX() - paintballSpeed);
			paintball.setY(paintball.getY() - paintballSpeed * slope);
		}
		else {
			paintball.setX(paintball.getX() + paintballSpeed);
			paintball.setY(paintball.getY() - paintballSpeed * slope);
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

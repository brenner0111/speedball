package com.speedball.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GunUtils {

	protected PaintballSprite createPaintballSprite(float x, float y, float slope) {
	    FileHandle paintBallHandle = Gdx.files.internal("redPaintball.png");
	    Texture backgroundTexture = new Texture(paintBallHandle);
	    return new PaintballSprite(backgroundTexture, slope);
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
			updatePaintballXY(paintball, paintballSpeed);
			paintball.draw(b);
		}
	}
	protected void updatePaintballXY(PaintballSprite paintball, float paintballSpeed) {
		paintball.setX(paintball.getX() + paintballSpeed);
		paintball.setY(paintball.getY() + paintballSpeed * paintball.getSlope());
	}
}

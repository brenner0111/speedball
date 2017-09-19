package com.speedball.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GunUtils {

	protected Sprite createPaintballSprite(float x, float y) {
	    FileHandle paintBallHandle = Gdx.files.internal("redPaintball.png");
	    Texture backgroundTexture = new Texture(paintBallHandle);
	    return new Sprite(backgroundTexture);
	}
	protected float getPlayerGunCoord(float coordinate, float offSet, boolean isX) {
	    //return coordniate of the tip of the gun barrel
	    if (isX) {
	        return coordinate + offSet;
	    }
	    else {
	        return coordinate + offSet;
	    }
	}
	
	protected void drawPaintballs(SpriteBatch b, ArrayList<Sprite> paintballs) {
		for (Sprite paintball : paintballs) {
			paintball.draw(b);
		}
	}
}

package com.speedball.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.speedball.game.PaintballMap;

public class DisplayScreen{

	private Texture startScreen;
	private Texture loadingScreen;
	private Texture gameScreen;
	private Texture victoryScreen;
	private Texture defeatScreen;
	private PaintballMap pbMap;

	public DisplayScreen() {
		//TODO:add new screens
		startScreen = new Texture("menus/startScreen.PNG");
		loadingScreen = new Texture("menus/loadingOne.PNG");
		gameScreen = new Texture("pbfield/grassBetter.PNG");
		victoryScreen = new Texture("menus/winningScreen.PNG");
		defeatScreen = new Texture("menus/defeatedScreen.PNG");
		pbMap = new PaintballMap(false);
		pbMap.createBunkers();
	}

	public void drawStartScreen(SpriteBatch batch) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.draw(startScreen, 0, 0);
	}

	public void drawLoadingScreen(SpriteBatch batch) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.draw(loadingScreen, 0, 0);
	}

	public void drawGameScreen(SpriteBatch batch) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.draw(gameScreen, 0, 0);
		pbMap.drawBunkers(batch);
		//TODO:draw HUD here

	}
	public void drawVictoryScreen(SpriteBatch batch) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.draw(victoryScreen, 0, 0);
	}

	public void drawDefeatScreen(SpriteBatch batch) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.draw(defeatScreen, 0, 0);
	}
	public Sprite createSprite(Texture texture, int initX, int initY) {
		return new Sprite(texture);
	}


}


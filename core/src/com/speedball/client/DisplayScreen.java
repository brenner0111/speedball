package com.speedball.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DisplayScreen{

	private Texture startScreen;
	private Texture loadingScreen;
	private Texture gameScreen;
	private Texture victoryScreen;
	private Texture defeatScreen;

	public DisplayScreen() {
		//TODO:add new screens
		startScreen = new Texture("pbfield/grassBetter.PNG");
		loadingScreen = new Texture("pbfield/grassBetter.PNG");
		gameScreen = new Texture("pbfield/grassBetter.PNG");
		victoryScreen = new Texture("pbfield/grassBetter.PNG");
		defeatScreen = new Texture("pbfield/grassBetter.PNG");
	}

	public void drawStartScreen(SpriteBatch batch) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(startScreen, 0, 0);
		batch.end();
	}

	public void drawLoadingScreen(SpriteBatch batch) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		batch.draw(loadingScreen, 0, 0);
		batch.end();
	}

	public void drawGameScreen(SpriteBatch batch) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(gameScreen, 0, 0);

		//TODO:draw HUD here

		batch.end();
	}
	public void drawVictoryScreen(SpriteBatch batch) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(victoryScreen, 0, 0);
		batch.end();
	}

	public void drawDefeatScreen(SpriteBatch batch) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(defeatScreen, 0, 0);
		batch.end();
	}
	public Sprite createSprite(Texture texture, int initX, int initY) {
		return new Sprite(texture);
	}


}


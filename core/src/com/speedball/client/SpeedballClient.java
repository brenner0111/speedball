package com.speedball.client;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.speedball.game.*;



public class SpeedballClient extends ApplicationAdapter{
	
	private final float GAME_WORLD_WIDTH = 1080;
	private final float GAME_WORLD_HEIGHT = 720;

	public volatile static float mouseAngle;
	
	private boolean displayStartScreen;
	private boolean displayGameScreen;
	private boolean displayLoadingScreen;
	private boolean displayVictoryScreen;
	private boolean displayDefeatScreen;
	private DisplayScreen displayScreen;
	private float mouseX;
	private float mouseY;
	private Player player;
	private SpriteBatch batch;
	private ClientNetworkThread ct;
	private Utils utils;
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	
	@Override
	public void create () {
		utils = new Utils();
		batch = new SpriteBatch();
		displayScreen = new DisplayScreen();
		//add methods for screen transitions
		initMouse();
		initViewport();
		initScreenStates();
		player = new Player(new Texture(Gdx.files.internal("player/playerNewSize.png")), 0f, 0f);
		ct = new ClientNetworkThread();
		ct.start();
		
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
	}

	@Override
	public void render () {
		getMouseCoords();
		renderLogic();
		batch.begin();
		processInputFromServer(batch);
		batch.end();
	}

	/*
	 * Need to look into making sure we are disposing the images properly
	 * @see com.badlogic.gdx.ApplicationAdapter#dispose()
	 */
	@Override
	public void dispose () {
		batch.dispose();
		player.getTexture().dispose();
		ct.interrupt();
	}
	
	private void initMouse() {
		mouseX = 0f;
		mouseY = 0f;
		mouseAngle = 0f;
	}
	private void initViewport() {
		camera = new OrthographicCamera();
		viewport = new StretchViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera);
		viewport.apply();
		camera.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);
		
	}
	private void initScreenStates() {
		displayStartScreen = true;
		displayGameScreen = false;
		displayVictoryScreen = false;
		displayDefeatScreen = false;
		displayLoadingScreen = false;
	}
	private void renderLogic() {
		updateScreenFlags();

		if (displayStartScreen) {
			displayScreen.drawStartScreen(batch);
		}
		else if (displayLoadingScreen) {
			displayScreen.drawLoadingScreen(batch);
		}
		else if (displayGameScreen) {
			displayScreen.drawGameScreen(batch);
		}
		else if (displayVictoryScreen) {
			displayScreen.drawVictoryScreen(batch);
		}
		else if (displayDefeatScreen) {
			displayScreen.drawDefeatScreen(batch);
		}
		else {
			System.out.println("Render Logic error");
		}
	}
	private void getMouseCoords() {
		if (Gdx.input.justTouched()) {
			Vector3 tmpCoords = new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
			camera.unproject(tmpCoords);
			System.out.println("World ClickXY: " + tmpCoords);
			mouseX = tmpCoords.x;
			mouseY = tmpCoords.y;
		}
	}
	private void setMouseAngle() {
		mouseAngle = utils.getMouseAngle(player.getPlayerX(), player.getPlayerY(), Player.getPlayerCenterWidth(), Player.getPlayerCenterHeight(), camera);
	}
	private void updateScreenFlags() {

		if (displayStartScreen) {
			/*if ((mouseX > 408f && mouseX < 662f) && (mouseY > 223f && mouseY < 280f)) {
				System.exit(0);
			}
			if((mouseX > 408f && mouseX < 662f) && (mouseY > 354 && mouseY < 410) ||
					(mouseX > 408f && mouseX < 662f) && (mouseY > 286f && mouseY < 345f)) {

				displayStartScreen = false;
				displayLoadingScreen = true;
			}*/
		}
		else if (displayLoadingScreen) {
			/*if(Gdx.input.justTouched()) {
				displayLoadingScreen = false;
				displayGameScreen = true;
			}*/
			/*try {
				TimeUnit.SECONDS.sleep(3);
				displayLoadingScreen = false;
				displayGameScreen = true;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

		}
		else if (displayGameScreen) {
			/*if(Gdx.input.justTouched()) {
				displayGameScreen = true;
				displayVictoryScreen = true;
			}*/
		}
		else if (displayVictoryScreen) {
			/*if(Gdx.input.justTouched()) {
				displayVictoryScreen = false;
				displayDefeatScreen = true;
			}*/
		/*	if ((mouseX > 320f && mouseX < 550f) && (mouseY > 37f && mouseY < 74f)) {
				displayVictoryScreen = false;
				displayDefeatScreen = true;
			}
			if ((mouseX > 608f && mouseX < 705f) && (mouseY > 30f && mouseY < 75f)) {
				System.exit(0);
			}*/
		}
		else if (displayDefeatScreen) {
			/*if(Gdx.input.justTouched()) {
				displayDefeatScreen = false;
				displayGameScreen = true;
			}*/
			/*if ((mouseX > 355f && mouseX < 575f) && (mouseY > 52f && mouseY < 88f)) {
				displayDefeatScreen = false;
				displayGameScreen = true;
			}
			if ((mouseX > 630f && mouseX < 724f) && (mouseY > 50f && mouseY < 88f)) {
				System.exit(0);
			}*/
		}
		else {
			System.out.println("Updating Screen flags error");
		}
	}
	private void processInputFromServer(SpriteBatch batch) {
		String tmp = ct.fromServer.substring(0, ct.fromServer.length());
		String[] strs = tmp.split("\\s+");
		System.out.println("Input from server: " + tmp);
		if (strs.length > 1) {
			for (int i = 0; i < strs.length; i++) {
				if (strs[i].equals("p")) {
					processPlayerData(strs, i);
					player = (Player) utils.rotateSprite(player.getMouseAngle(), player, Player.getPlayerCenterWidth(), Player.getPlayerCenterHeight(), player.getPlayerX(), player.getPlayerY());
					player.setBounds(player.getPlayerX(), player.getPlayerY(), Player.getPlayerWidth(), Player.getPlayerHeight());
					player.draw(batch);
				}
			}
		}
	}
	private void processPlayerData(String[] strs, int index) {
		player.setPlayerX(Float.parseFloat(strs[index + 1]));
		player.setPlayerY(Float.parseFloat(strs[index + 2]));
		setMouseAngle();
		player.setMouseAngle(Float.parseFloat(strs[index + 3]));
		//TODO: Need to find solution for rotation
	}
}

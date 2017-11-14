package com.speedball.client;

import java.util.ArrayList;
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
	public volatile static String paintballInfo;

	private boolean displayStartScreen;
	private boolean displayGameScreen;
	private boolean displayLoadingScreen;
	private boolean displayVictoryScreen;
	private boolean displayDefeatScreen;
	private DisplayScreen displayScreen;
	private float mouseX;
	private float mouseY;
	public volatile Player[] players = new Player[2];
	public ArrayList<Paintball> paintballs = new ArrayList<Paintball>();
	public int paintballCounter = 0;
	private SpriteBatch batch;
	private ClientNetworkThread ct;
	private Utils utils;
	private GunUtils gunUtils;
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	
	@Override
	public void create () {
		utils = new Utils();
		gunUtils = new GunUtils();
		batch = new SpriteBatch();
		displayScreen = new DisplayScreen();
		//add methods for screen transitions
		initMouse();
		initViewport();
		initScreenStates();
//		initArrayLists();
		players[0] = new Player(new Texture(Gdx.files.internal("player/playerNewSize.png")), 0f, 0f);
		players[1] = new Player(new Texture(Gdx.files.internal("player/playerNewSize.png")), 0f, 0f);
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
		batch.begin();
		renderLogic();
		processInputFromServer(batch);
		//System.out.println("Player1 X: " + players[0].getPlayerX() + " Player1 Y: " + players[0].getPlayerY());
		//System.out.println("Player2 X: " + players[1].getPlayerX() + " Player2 Y: " + players[1].getPlayerY());
		batch.end();
	}

	/*
	 * Need to look into making sure we are disposing the images properly
	 * @see com.badlogic.gdx.ApplicationAdapter#dispose()
	 */
	@Override
	public void dispose () {
		batch.dispose();
		for (Player player: players) {
			player.getTexture().dispose();
		}
		ct.interrupt();
	}
//	private void initArrayLists() {
//		paintballX = new ArrayList<Float>();
//		paintballY = new ArrayList<Float>();
//		paintballSlope = new ArrayList<Float>();
//		paintballQuadrant = new ArrayList<Integer>();
//	}
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
			//System.out.println("World ClickXY: " + tmpCoords);
			mouseX = tmpCoords.x;
			mouseY = tmpCoords.y;
		}
	}
	private float getSlopeBtnGunAndMouse(Player player) {
		float centerX = player.getPlayerX() + Player.getPlayerCenterWidth();
        float centerY = player.getPlayerY() + Player.getPlayerCenterHeight();
        float[] coords = gunUtils.updateRealGunXY(mouseAngle, centerX, centerY, Player.getGunRadius());
        float mouseX = Gdx.input.getX();
        Vector3 tmpCoords = new Vector3(mouseX,Gdx.input.getY(), 0);
        camera.unproject(tmpCoords);
        float slope = (tmpCoords.y - coords[1]) / (tmpCoords.x - coords[0]);
        return slope;
	}
	private float[] getGunXY(Player player) {
		float centerX = player.getPlayerX() + Player.getPlayerCenterWidth();
        float centerY = player.getPlayerY() + Player.getPlayerCenterHeight();
        float[] coords = gunUtils.updateRealGunXY(mouseAngle, centerX, centerY, Player.getGunRadius());
        return coords;
	}
	private int getQuadrant() {
		return gunUtils.checkQuadrant(mouseAngle);
	}
	private void setPaintballString(Player player) {
		float slope = getSlopeBtnGunAndMouse(player);
		float[] gunXY = getGunXY(player);
		paintballInfo = "" + slope + " " + getQuadrant() + " " + gunXY[0] + " " + gunXY[1];
	}
		
	private void setMouseAngle(float playerX, float playerY) {
		//System.out.println("PlayerX: " + playerX + " PlayerY: " +  playerY);
		mouseAngle = utils.getMouseAngle(playerX, playerY, Player.getPlayerCenterWidth(), Player.getPlayerCenterHeight(), camera);
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
		paintballs = new ArrayList<Paintball>();
		String tmp = ct.fromServer.substring(0, ct.fromServer.length());
		//System.out.println("From server: " + tmp);
		String[] strs = tmp.split("\\s+");
		//System.out.println("Strs[0]: " + strs[0]);
		int whichPlayer = -1;
		if (strs[0] != "") {
			whichPlayer = Integer.parseInt(strs[0]) + 1;
		}
		if (strs.length > 1) {
			for (int i = 0; i < strs.length; i++) {
				if (strs[i].equals("p1")) {
					players[0] = (Player) utils.rotateSprite(Float.parseFloat(strs[i + 3]), players[0], Player.getPlayerCenterWidth(), Player.getPlayerCenterHeight(), Float.parseFloat(strs[i + 1]), Float.parseFloat(strs[i + 2]));
					players[0].setBounds(Float.parseFloat(strs[i + 1]), Float.parseFloat(strs[i + 2]), Player.getPlayerWidth(), Player.getPlayerHeight());
					players[0].setPlayerX(Float.parseFloat(strs[i+1]));
					players[0].setPlayerY(Float.parseFloat(strs[i+2]));
					if (whichPlayer == 1) {
						setMouseAngle(Float.parseFloat(strs[i + 1]), Float.parseFloat(strs[i + 2]));
						setPaintballString(players[0]);
					}
					players[0].draw(batch);
				}
				else if (strs[i].equals("p2")) {
					players[1] = (Player) utils.rotateSprite(Float.parseFloat(strs[i + 3]), players[1], Player.getPlayerCenterWidth(), Player.getPlayerCenterHeight(), Float.parseFloat(strs[i + 1]), Float.parseFloat(strs[i + 2]));
					players[1].setBounds(Float.parseFloat(strs[i + 1]), Float.parseFloat(strs[i + 2]), Player.getPlayerWidth(), Player.getPlayerHeight());
					players[1].setPlayerX(Float.parseFloat(strs[i+1]));
					players[1].setPlayerY(Float.parseFloat(strs[i+2]));
					if (whichPlayer == 2) {
						setMouseAngle(Float.parseFloat(strs[i + 1]), Float.parseFloat(strs[i + 2]));
						setPaintballString(players[1]);
					}
					players[1].draw(batch);
				}
				else if (strs[i].equals("pb1")) {
					//slope and quadrant are no longer used in pbClient...TODO: if have time
					Paintball paintball = new Paintball(new Texture(Gdx.files.internal("paintballs/redPaintball.png")), Float.parseFloat(strs[i+1]), Integer.parseInt(strs[i+2]));
					paintball.setBounds(Float.parseFloat(strs[i+3]), Float.parseFloat(strs[i+4]), 4, 4);
					paintballs.add(paintball);
					paintballCounter++;
					gunUtils.drawPaintballsClient(batch, paintballs, paintballCounter);
				}
				else if (strs[i].equals("pb2")) {
					Paintball paintball = new Paintball(new Texture(Gdx.files.internal("paintballs/redPaintball.png")), Float.parseFloat(strs[i+1]), Integer.parseInt(strs[i+2]));
					paintball.setBounds(Float.parseFloat(strs[i+3]), Float.parseFloat(strs[i+4]), 4, 4);
					paintballs.add(paintball);
					paintballCounter++;
					gunUtils.drawPaintballsClient(batch, paintballs, paintballCounter);
				}
			}
		}
	}
}

package com.speedball.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.speedball.*;
import com.speedball.game.GunUtils;
import com.speedball.game.Paintball;
import com.speedball.game.PaintballMap;
import com.speedball.game.Player;
import com.speedball.game.Utils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;	

public class SpeedballClientOld extends ApplicationAdapter
{
	private static final float MAX_X = 1045f;
	private static final float MAX_Y = 690f;
	private final float GAME_WORLD_WIDTH = 1080;
	private final float GAME_WORLD_HEIGHT = 720;

	private Utils utils = new Utils();
	private GunUtils gunUtils = new GunUtils();
	private PaintballMap bunker = new PaintballMap();
	private Player player;
	private Sprite background;
	private Cursor customCursor;
	private Texture cursor;
	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch batch;

	private Socket clientSocket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	private String dataFromServer;


	@Override
	public void create () {
		batch = new SpriteBatch();
		player = utils.createPlayerSprite(0f, 0f);
		player.setPosition(0, 0);
		background = utils.createBackgroundSprite();
		background.setPosition(0, 0);
		background.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
		camera = new OrthographicCamera();
		viewport =  new StretchViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera);
		viewport.apply();
		camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
		//player.setPaintballCounter(-1);
		//player.setPlayerX(player.getInitX());
		//player.setPlayerY(player.getInitY());
		cursor = new Texture("misc/crossHair.PNG");
		customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("misc/crossHair.PNG")), cursor.getWidth()/2, cursor.getHeight()/2);
		Gdx.graphics.setCursor(customCursor);	
		bunker.createBunkers();
		//might want to move connectToServer() to render method to be called after the player goes through the menus
		connectToServer();

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		sendAndRecieveFromServer();

		updateGameState();
		updateSpriteState();

		batch.end();
	}

	/*
	 * Need to look into making sure we are disposing the images properly
	 * @see com.badlogic.gdx.ApplicationAdapter#dispose()
	 */
	@Override
	public void dispose () {
		batch.dispose();
	}

	private void connectToServer() {
		try {
			//clientSocket = new Socket("54.164.13.70", 6789);
			clientSocket = new Socket("localhost", 6789);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	private void sendAndRecieveFromServer() {
		try {
			//Send to server
			outToServer.writeBytes("data" + '\n');
			//receive from server
			dataFromServer = inFromServer.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateSpriteState() {		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		background.draw(batch);
		bunker.drawBunkers(batch);
		player.setPaintballCounter(gunUtils.drawPaintballs(batch, player.getPaintballs(), player.getPaintballCounter(), bunker.getBunkers()));

		player.setMouseAngle(utils.getMouseAngle(player.getPlayerX(), player.getPlayerY(), Player.getPlayerCenterWidth(), Player.getPlayerCenterHeight(), camera));
		player = (Player) utils.rotateSprite(player.getMouseAngle(), player, Player.getPlayerCenterWidth(), Player.getPlayerCenterHeight(),
				player.getPlayerX(), player.getPlayerY());
		player.draw(batch);
	}

	private void updateGameState() {
		if(gunUtils.checkAndFireGun(player.getMouseAngle(), player, camera)) {
			player.getPaintballs().get(player.getPaintballCounter()).setBounds(player.getGunX(), player.getGunY(), 4, 4);

			player.getPaintballs().set(player.getPaintballCounter(), (Paintball) utils.rotateSprite(player.getMouseAngle(),
					player.getPaintballs().get(player.getPaintballCounter()), Player.getPlayerCenterWidth() - Player.getPlayerGunWidth(),
					Player.getPlayerCenterHeight() - Player.getPlayerGunHeight(), player.getPlayerX(), player.getPlayerY()));

			player.getPaintballs().get(player.getPaintballCounter()).draw(batch);
		}
		//checks to make sure player is in bounds, and calls movePlayer
		utils.playerMtvLogic(player, bunker);
		utils.checkAndMovePlayer(player.getPlayerX(), player.getPlayerY(), MAX_X, MAX_Y, player);
	}


	/*public static void main(String[] args) throws UnknownHostException, IOException {
        //sets the socket to the local address 
        //Socket clientSocket = new Socket(InetAddress.getLocalHost().getHostAddress(), 6789);
        Socket clientSocket = new Socket("54.164.13.70", 6789);

        String sentence;
        String modifiedSentence;

        //streams
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (true) {

            System.out.println("Connected to server! Type something.");

            //Send to server
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');

            //receive from server
            modifiedSentence = inFromServer.readLine();
            System.out.println("from server: " + modifiedSentence);
        }
        //clientSocket.close();
    }*/
}

package com.speedball.game;

import java.util.ArrayList;

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

/**
 * Testing committing to Git with Eclipse
 * @author Calvin Yarboro
 *
 */
public class SpeedBall extends ApplicationAdapter {
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
	
	private float mouseAngle;
	

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
				
		paintballCounter = -1;
		paintballs = new ArrayList<Paintball>();
		player.setPlayerX(player.getInitX());
		player.setPlayerY(player.getInitY());

		cursor = new Texture("misc/crossHair.PNG");
		customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("misc/crossHair.PNG")), cursor.getWidth()/2, cursor.getHeight()/2);
		Gdx.graphics.setCursor(customCursor);
		
		bunker.createBunkers();
		
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
		updateSpriteState();
		updateGameState();
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
	
	private void updateSpriteState() {		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
        background.draw(batch);
        bunker.drawBunkers(batch);
        paintballCounter = gunUtils.drawPaintballs(batch, paintballs, paintballCounter, bunker.getBunkers());
        utils.playerCollided(player, bunker.getBunkers());
        if (player.getCollided() == false) {
            player.setBounds(player.getPlayerX(), player.getPlayerY(), Player.getPlayerWidth(), Player.getPlayerHeight());
        }
        else {
        	System.out.println("Stuck");
        	player.setPlayerX(player.getPlayerX() - 10);
        	player.setPlayerY(player.getPlayerY() - 10);
  
        	player.setBounds(player.getPlayerX(), player.getPlayerY(), Player.getPlayerWidth(), Player.getPlayerHeight());
        }
        mouseAngle = utils.getMouseAngle(player.getPlayerX(), player.getPlayerY(), Player.getPlayerCenterWidth(), Player.getPlayerCenterHeight(), camera);
        player = (Player) utils.rotateSprite(mouseAngle, player, Player.getPlayerCenterWidth(), Player.getPlayerCenterHeight(), player.getPlayerX(), player.getPlayerY());
        player.draw(batch);
        
	}
	private void updateGameState() {
	    if(gunUtils.checkAndFireGun(mouseAngle, player, camera, paintballs, paintballCounter)) {
            paintballs.get(paintballCounter).setBounds(player.getGunX(), player.getGunY(), 4, 4);
            paintballs.set(paintballCounter, (Paintball) utils.rotateSprite(mouseAngle, paintballs.get(paintballCounter),
            		Player.getPlayerCenterWidth() - Player.getPlayerGunWidth(), Player.getPlayerCenterHeight() - Player.getPlayerGunHeight(), player.getPlayerX(), player.getPlayerY()));
            paintballs.get(paintballCounter).draw(batch);
        }
	    //checks to make sure player is in bounds, and calls movePlayer
	    utils.checkAndMovePlayer(player.getPlayerX(), player.getPlayerY(), MAX_X, MAX_Y, player);
	}

	
}

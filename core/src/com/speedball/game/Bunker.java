package com.speedball.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bunker
{
    //private static final FileHandle DORITO_LARGE = Gdx.files.internal("bunkers/doritoLarge.png");
    //private FileHandle DORITO_LARGE_RIGHT = Gdx.files.internal("bunkers/doritoLargeRight.png");
    //private static final FileHandle DORITO_LARGE_LEFT = Gdx.files.internal("bunkers/doritoLargeLeft.png");

//    private static final FileHandle DORITO_SMALL = Gdx.files.internal("bunkers/doritoSmall.png");
//    private static final FileHandle PIN = Gdx.files.internal("bunkers/pin.png");
//    private static final FileHandle RECTANGLE = Gdx.files.internal("bunkers/rectangle.png");
//    private static final FileHandle SNAKE = Gdx.files.internal("bunkers/snake.png");
//    private static final FileHandle SQUARE_BUNKER = Gdx.files.internal("bunkers/squareBunker.png");
//    private static final FileHandle X = Gdx.files.internal("bunkers/x.png");
//    private static final FileHandle DEAD_BOX = Gdx.files.internal("bunkers/deadbox.png");
    
    private ArrayList<Sprite> bunkers = new ArrayList<Sprite>();
    
    
    
    protected void createBunkerSprite(FileHandle file, float x, float y, float width, float height) {
        Sprite bunker = new Sprite(new Texture(file));
        bunker.setBounds(x, y, width, height);
        bunkers.add(bunker);
    }

    protected void drawBunkers(Batch b) {
        for (int i = 0; i < bunkers.size(); i++)
            bunkers.get(i).draw(b);
    }
    
    protected void createBunkers() {
        createLargeDoritos();
//        createSmallDoritos();
//        createPins();
//        createRectangles();
//        createSnake();
//        createSquareBunkers();
//        createX();
//        createDeadBox();
        
    }
    protected void createLargeLeftDoritos() {
    	FileHandle doritoLargeLeft = Gdx.files.internal("bunkers/doritoLargeLeft.png");
    	createBunkerSprite(doritoLargeLeft, 955.0f, 340.0f, 32.8f, 30.4f);
    }
    protected void createLargeRightDoritos() {
    	FileHandle doritoLargeRight = Gdx.files.internal("bunkers/doritoLargeRight.png");
    	createBunkerSprite(doritoLargeRight, 93.93f, 348.31863f, 32.8f, 30.4f);
    	
    }
    protected void createLargeDoritos() {
    	createLargeRightDoritos();
    	createLargeLeftDoritos();
    }
    
    protected void createSmallDoritos() {
        
    }
    
    protected void createPins() {
        
    }
    
    protected void createRectangles() {
        
    }
    
    protected void createSnake() {
        
    }
    
    protected void createSquareBunkers() {
        
    }
    
    protected void createX() {
        
    }
    protected void createDeadBox() {
        
    }
    
    
}

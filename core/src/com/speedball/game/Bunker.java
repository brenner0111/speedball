package com.speedball.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

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
    
    private ArrayList<BunkerSprite> bunkers = new ArrayList<BunkerSprite>();
    
    protected void createBunkerSprite(FileHandle file, float x, float y, float width, float height, float[] vertices) {
        BunkerSprite bunker = new BunkerSprite(new Texture(file), vertices);
        bunker.setBounds(x, y, width, height);
        bunkers.add(bunker);
    }

    protected void drawBunkers(Batch b) {
        for (int i = 0; i < bunkers.size(); i++) {
        	bunkers.get(i).draw(b);
        }
    }
    
    protected void createBunkers() {
        createLargeDoritos();
//        createSmallDoritos();
//        createPins();
//        createRectangles();
//        createSnake();
//        createSquareBunkers();
//        createX();
        createDeadBox();
        
    }
    protected void createLargeLeftDoritos() {
    	FileHandle doritoLargeLeft = Gdx.files.internal("bunkers/doritoLargeLeft.png");
    	float[] vertices1 = {955.687f, 354.69f, 983.81f, 341.238f, 983.81f, 369.557f};
    	createBunkerSprite(doritoLargeLeft, 955.0f, 348.31863f, 32.8f, 30.4f, vertices1);
    }
    protected void createLargeRightDoritos() {
    	FileHandle doritoLargeRight = Gdx.files.internal("bunkers/doritoLargeRight.png");
    	float[] vertices1 = {93.93f, 354.69f, 124.875f, 363.1858f, 93.93f, 377.345f};
    	createBunkerSprite(doritoLargeRight, 93.93f, 348.31863f, 32.8f, 30.4f, vertices1);
    	
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
    	FileHandle deadBox = Gdx.files.internal("misc/deadbox.png");
    	float[] vertices1 = {19.125f, 313.628f, 46.687f, 313.628f, 46.687f, 405.664f, 19.125f, 405.664f};
        float[] vertices2 = {1031.625f, 313.628f, 1059.188f, 313.628f, 1059.188f, 405.664f, 1031.625f, 405.664f};
        createBunkerSprite(deadBox, 19.125f, 303.628f, 40f, 112f, vertices1);
        createBunkerSprite(deadBox, 1020.625f, 303.628f, 40f, 112f, vertices2);
    }
    
    
}

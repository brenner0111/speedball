package com.speedball.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Bunker
{
    private ArrayList<BunkerSprite> bunkers = new ArrayList<BunkerSprite>();
    private ArrayList<BunkerSprite> collidableBunkers = new ArrayList<BunkerSprite>();
    
    
    protected void createBunkerSprite(FileHandle file, float x, float y, float width, float height, float[] vertices) {
        FileHandle deadBox = Gdx.files.internal("misc/deadbox.png");
        BunkerSprite bunker = new BunkerSprite(new Texture(file), vertices);
        bunker.setBounds(x, y, width, height);
        bunkers.add(bunker);
        if (!file.equals(deadBox)) {
            collidableBunkers.add(bunker);
        }
    }

    protected void drawBunkers(Batch b) {
        for (int i = 0; i < bunkers.size(); i++) {
        	bunkers.get(i).draw(b);
        }
    }
    
    protected void createBunkers() {
        createDoritos();
        createRectangles();
        createPins();
        createSnake();
        createSquareBunkers();
        createX();
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
    protected void createDoritos() {
    	createLargeRightDoritos();
    	createLargeLeftDoritos();
    	createTopDoritos();
    }
    
    protected void createTopDoritos() {
        FileHandle smallDorito = Gdx.files.internal("bunkers/doritoLarge.png");
        float[] d1 = {296.4f, 662.85724f, 286.2f, 643.8096f, 308.4f, 643.04767f};
        float[] d2 = {397.80005f, 666.6667f, 387.00006f, 645.33344f, 409.20004f, 643.8096f};
        float[] d3 = {503.4001f, 665.1429f, 493.80005f, 643.8096f, 516.00006f, 644.5715f};
        float[] d4 = {611.4f, 665.1429f, 601.2001f, 643.04767f, 622.80005f, 643.04767f};
        float[] d5 = {703.80005f, 666.6667f, 693.60004f, 644.5715f, 714.00006f, 643.8096f};
        float[] d6 = {774.6001f, 667.42865f, 763.80005f, 644.5715f, 785.4001f, 644.5715f};
        createBunkerSprite(smallDorito, 286.2f, 643.8096f, 32.8f, 30.4f, d1);
        createBunkerSprite(smallDorito, 387.00006f, 645.33344f, 32.8f, 30.4f, d2);
        createBunkerSprite(smallDorito, 493.80005f, 643.8096f, 32.8f, 30.4f, d3);
        createBunkerSprite(smallDorito, 601.2001f, 643.04767f, 32.8f, 30.4f, d4);
        createBunkerSprite(smallDorito, 693.60004f, 644.5715f, 32.8f, 30.4f, d5);
        createBunkerSprite(smallDorito, 763.80005f, 644.5715f, 32.8f, 30.4f, d6);
    }
    
    protected void createPins() {
        FileHandle pin = Gdx.files.internal("bunkers/pin.png");
        float[] topLeftPin = {};
        float[] topRightPin = {};
        float[] bottomLeftPin = {};
        float[] bottomRightPin = {};
        float[] attachedPin = {};
        createBunkerSprite(pin, 243.56088f, 532.8672f, 25f, 25f, topLeftPin);
        createBunkerSprite(pin, 814.80725f, 533.70636f, 25f, 25f, topRightPin);
        createBunkerSprite(pin, 245.16324f, 134.26573f, 25f, 25f, bottomLeftPin);
        createBunkerSprite(pin, 818.8132f, 132.58745f, 25f, 25f, bottomRightPin);
        createBunkerSprite(pin, 540.00006f, 533.70636f, 25f, 25f, attachedPin);
        
    }
    
    protected void createRectangles() {
        FileHandle rectangle = Gdx.files.internal("bunkers/rectangle.png");
        float[] topLeftRectangle = {};
        float[] topRightRectangle = {};
        float[] bottomLeftRectangle = {};
        float[] bottomRightRectangle = {};
        float[] attachedRectangle = {};
        createBunkerSprite(rectangle, 248.85364f, 411.96658f, 18.72f, 30.24f, topLeftRectangle);
        createBunkerSprite(rectangle, 820.72656f, 424.7699f, 18.72f, 30.24f, topRightRectangle);
        createBunkerSprite(rectangle, 247.0148f, 281.67368f, 18.72f, 30.24f, bottomLeftRectangle);
        createBunkerSprite(rectangle, 818.8877f, 274.89542f, 18.72f, 30.24f, bottomRightRectangle);
        createBunkerSprite(rectangle, 545.06256f, 550.7966f, 18.72f, 30.24f, attachedRectangle);
    }
    
    protected void createSnake() {
        FileHandle snake = Gdx.files.internal("bunkers/snake.png");
        float[] vertices = {};
        createBunkerSprite(snake, 350.00006f, 70.00001f, 378.7f, 114.1f, vertices);
        
    }
    
    protected void createSquareBunkers() {
        FileHandle squareBunker = Gdx.files.internal("bunkers/squareBunker.png");
        float[] topLeftVertices = {118.435104f, 660.9977f, 119.0753f, 637.9098f, 137.6408f, 637.9098f, 137.6408f, 660.14264f};
        float[] topRightVertices = {912.91064f, 661.8528f, 913.5508f, 640.47516f, 934.03687f, 638.76495f, 934.67706f, 663.563f};       
        float[] bottomLeftVertices = {122.95387f, 72.93287f, 120.96001f, 50.035347f, 143.55693f, 50.8834f, 143.55693f, 72.93287f};       
        float[] bottomRightVertices = {922.4863f, 76.32511f, 922.4863f, 52.579506f, 943.754f, 54.27561f, 943.754f, 76.32511f};
        float[] midTopLeftVertices = {444.62778f, 461.3428f, 443.96313f, 439.29333f, 465.23083f, 438.44528f, 465.23083f, 460.49475f};        
        float[] midTopRightVertices = {616.7631f, 460.49475f, 616.0985f, 437.59723f, 638.69543f, 436.74918f, 638.69543f, 460.49475f};        
        float[] midBottomLeftVertices = {443.96313f, 306.14844f, 445.95697f, 283.25092f, 467.88928f, 283.25092f, 465.89545f, 306.9965f};
        float[] midBottomRightVertices = {622.08f, 307.84457f, 620.75085f, 285.79507f, 646.00616f, 284.94705f, 644.0124f, 306.9965f};  
        createBunkerSprite(squareBunker, 119.0753f, 637.9098f, 28.16f, 27.52f, topLeftVertices);
        createBunkerSprite(squareBunker, 913.5508f, 640.47516f, 28.16f, 27.52f, topRightVertices);
        createBunkerSprite(squareBunker, 120.96001f, 50.035347f,28.16f, 27.52f, bottomLeftVertices);      
        createBunkerSprite(squareBunker, 922.4863f, 52.579506f, 28.16f, 27.52f, bottomRightVertices);   
        createBunkerSprite(squareBunker, 443.96313f, 439.29333f, 28.16f, 27.52f, midTopLeftVertices);        
        createBunkerSprite(squareBunker, 616.0985f, 437.59723f, 28.16f, 27.52f, midTopRightVertices);
        createBunkerSprite(squareBunker, 445.95697f, 283.25092f, 28.16f, 27.52f, midBottomLeftVertices);        
        createBunkerSprite(squareBunker, 620.75085f, 285.79507f, 28.16f, 27.52f, midBottomRightVertices);     
    }
    
    protected void createX() {
        FileHandle x = Gdx.files.internal("bunkers/x.png");
        float[] vertices = {533.30334f, 400.41583f, 522.34503f, 386.94394f, 536.3473f, 367.48444f, 522.34503f, 347.27652f, 533.30334f,
            333.05618f, 551.56714f, 348.7734f, 566.787f, 332.3077f, 578.35406f, 348.02496f, 564.96063f, 365.98755f, 580.1805f, 386.1955f,
            566.787f, 399.6674f, 550.34955f, 384.6986f};
        createBunkerSprite(x, 520.5187f, 329.31393f, 67.84f, 67.84f, vertices);
    }
    protected void createDeadBox() {
    	FileHandle deadBox = Gdx.files.internal("misc/deadbox.png");
    	float[] vertices1 = {19.125f, 313.628f, 46.687f, 313.628f, 46.687f, 405.664f, 19.125f, 405.664f};
        float[] vertices2 = {1031.625f, 313.628f, 1059.188f, 313.628f, 1059.188f, 405.664f, 1031.625f, 405.664f};
        createBunkerSprite(deadBox, 19.125f, 303.628f, 40f, 112f, vertices1);
        createBunkerSprite(deadBox, 1020.625f, 303.628f, 40f, 112f, vertices2);
    }
    protected ArrayList<BunkerSprite> getCollidableBunkers(){
        return this.collidableBunkers;
    }
    
}

package com.speedball.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class PaintballMap
{
    private ArrayList<Bunker> bunkers = new ArrayList<Bunker>();
    
    protected void createBunkerSprite(FileHandle file, float x, float y, float width, float height, float[] vertices) {
        FileHandle deadBox = Gdx.files.internal("misc/deadbox.png");
        Bunker bunker;
        if (!file.equals(deadBox)) {
        	bunker = new Bunker(new Texture(file), vertices, true);
        }
        else {
        	bunker = new Bunker(new Texture(file), vertices, false);
        }
        bunker.setBounds(x, y, width, height);
        bunkers.add(bunker);
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
        createLongRectangle();
        
    }
    protected void createLargeLeftDoritos() {
    	FileHandle doritoLargeLeft = Gdx.files.internal("bunkers/doritoLargeLeft.png");
    	float[] vertices1 = {96.187515f, 375.22128f, 96.187515f, 349.02658f, 126.000015f, 362.4779f};
    	createBunkerSprite(doritoLargeLeft, 955.0f, 348.31863f, 32.8f, 30.4f, vertices1);
    }
    protected void createLargeRightDoritos() {
    	FileHandle doritoLargeRight = Gdx.files.internal("bunkers/doritoLargeRight.png");
    	float[] vertices1 = {986.0626f, 378.05313f, 955.6876f, 363.18585f, 986.0626f, 350.4425f};
    	createBunkerSprite(doritoLargeRight, 93.93f, 348.31863f, 32.8f, 30.4f, vertices1);
    	
    }
    protected void createDoritos() {
    	createLargeRightDoritos();
    	createLargeLeftDoritos();
    	createTopDoritos();
    }
    
    protected void createTopDoritos() {
        FileHandle dorito = Gdx.files.internal("bunkers/doritoLarge.png");
        float[] d1 = {297.56253f, 663.36285f, 282.37503f, 637.87616f, 312.75003f, 637.87616f};
        float[] d2 = {398.81256f, 663.36285f, 383.62503f, 637.87616f, 414.00006f, 637.87616f};
        float[] d3 = {500.06256f, 663.36285f, 484.87506f, 637.87616f, 515.25006f, 637.87616f};
        float[] d4 = {592.87506f, 663.36285f, 578.25006f, 637.87616f, 607.50006f, 637.87616f};
        float[] d5 = {684.56256f, 663.36285f, 670.50006f, 637.87616f, 700.31256f, 637.87616f};
        float[] d6 = {779.6251f, 663.36285f, 765.56256f, 637.87616f, 795.9376f, 637.87616f};
        createBunkerSprite(dorito, 281.2f, 635.47516f, 32.8f, 30.4f, d1);
        createBunkerSprite(dorito, 382.00006f, 635.47516f, 32.8f, 30.4f, d2);
        createBunkerSprite(dorito, 483.80005f, 635.47516f, 32.8f, 30.4f, d3);
        createBunkerSprite(dorito, 576.2001f, 635.47516f, 32.8f, 30.4f, d4);
        createBunkerSprite(dorito, 668.60004f, 635.47516f, 32.8f, 30.4f, d5);
        createBunkerSprite(dorito, 763.80005f, 635.47516f, 32.8f, 30.4f, d6);
    }
    
    protected void createPins() {
        FileHandle pin = Gdx.files.internal("bunkers/pin.png");
        float[] topLeftPin = {250.3125f, 557.1682f, 242.43753f, 555.0443f, 239.62503f, 545.1328f, 243.00003f, 536.6372f,
        		251.43753f, 533.80536f, 259.87503f, 537.34515f, 263.25003f, 545.1328f, 259.87503f, 553.62836f};
        float[] topRightPin = {826.8751f, 557.87616f, 819.0001f, 555.0443f, 815.6251f, 544.42487f, 820.1251f, 535.92926f,
        		826.3126f, 534.51337f, 835.8751f, 538.76117f, 839.2501f, 545.84076f, 835.3126f, 554.33636f};
        float[] bottomLeftPin = {250.3125f, 194.69029f, 243.00003f, 191.15045f, 239.62503f, 180.53099f, 243.00003f, 173.45135f,
        		252.00006f, 169.91151f, 258.75f, 172.74338f, 262.12503f, 181.23895f, 259.31253f, 191.15045f};
        float[] bottomRightPin = {827.4376f, 193.98232f, 819.5626f, 192.56639f, 815.6251f, 179.82303f, 819.0001f, 173.45135f,
        		827.4376f, 169.91151f, 834.7501f, 174.15932f, 839.8126f, 181.94691f, 834.7501f, 191.15045f};
        float[] attachedPin = {543.37506f, 557.87616f, 536.06256f, 554.33636f, 532.68756f, 543.71686f, 536.62506f, 535.92926f,
        		544.50006f, 533.80536f, 552.93756f, 538.05316f, 556.31256f, 545.84076f, 552.93756f, 554.33636f};
        createBunkerSprite(pin, 239.0148f, 533.70636f, 25f, 25f, topLeftPin);
        createBunkerSprite(pin, 814.80725f, 533.70636f, 25f, 25f, topRightPin);
        createBunkerSprite(pin, 239.0148f, 170.26573f, 25f, 25f, bottomLeftPin);
        createBunkerSprite(pin, 814.80725f, 170.26573f, 25f, 25f, bottomRightPin);
        createBunkerSprite(pin, 532.00006f, 533.70636f, 25f, 25f, attachedPin);
        
    }
    
    protected void createRectangles() {
        FileHandle rectangle = Gdx.files.internal("bunkers/rectangle.png");
        float[] topLeftRectangle = {243.56256f, 466.54874f, 243.56256f, 438.23013f, 259.31253f, 438.23013f, 259.31253f, 465.84076f};
        float[] topRightRectangle = {820.1251f, 465.84076f, 820.1251f, 437.52216f, 835.8751f, 438.23013f, 836.4376f, 465.84076f};
        float[] bottomLeftRectangle = {243.56256f, 292.38943f, 243.56256f, 264.07083f, 259.31253f, 264.07083f, 259.31253f, 291.68143f};
        float[] bottomRightRectangle = {820.1251f, 291.68143f, 820.1251f, 263.36288f, 835.8751f, 264.7788f, 836.4376f, 291.68143f};
        float[] attachedRectangle = {536.62506f, 579.1151f, 537.18756f, 557.1682f, 552.37506f, 556.46027f, 552.37506f, 579.1151f};
        createBunkerSprite(rectangle, 242.0148f, 437.59723f, 18.72f, 30.24f, topLeftRectangle);
        createBunkerSprite(rectangle, 818.8877f, 437.59723f, 18.72f, 30.24f, topRightRectangle);
        createBunkerSprite(rectangle, 242.0148f, 263.25092f, 18.72f, 30.24f, bottomLeftRectangle);
        createBunkerSprite(rectangle, 818.8877f, 263.25092f, 18.72f, 30.24f, bottomRightRectangle);
        createBunkerSprite(rectangle, 535.06256f, 550.7966f, 18.72f, 30.24f, attachedRectangle);
    }
    
    protected void createSnake() {
        FileHandle snake = Gdx.files.internal("bunkers/snake.png");
        float[] vertices = {354.93753f, 102.65489f, 354.37503f, 89.20353f, 394.87506f, 86.37168f, 404.43756f, 69.38053f,
        		415.12506f, 85.663704f, 460.12506f, 88.49558f, 529.31256f, 155.04427f, 548.43756f, 155.04427f, 616.50006f, 87.787605f,
        		662.62506f, 86.37168f, 674.43756f, 69.38053f, 686.25006f, 87.07966f, 722.25006f, 88.49558f, 722.25006f, 102.65489f,
        		623.25006f, 102.65489f, 555.75006f, 168.49559f, 552.93756f, 181.23895f, 527.06256f, 181.94691f, 522.00006f, 167.07967f,
        		455.06256f, 103.36284f};
        createBunkerSprite(snake, 350.00006f, 70.00001f, 378.7f, 114.1f, vertices);
        
    }
    
    protected void createSquareBunkers() {
        FileHandle squareBunker = Gdx.files.internal("bunkers/squareBunker.png");
        float[] topLeftVertices = {119.812515f, 665.4868f, 121.500015f, 642.12396f, 145.68752f, 642.8319f, 144.56248f, 665.4868f};
        float[] topRightVertices = {914.6251f, 666.9027f, 915.7501f, 642.12396f, 940.5001f, 642.12396f, 939.9376f, 666.19476f};       
        float[] bottomLeftVertices = {122.062515f, 76.46018f, 122.062515f, 51.681404f, 147.37502f, 51.681404f, 147.37502f, 75.04426f};       
        float[] bottomRightVertices = {924.1876f, 75.752235f, 924.7501f, 51.681404f, 948.9376f, 51.681404f, 947.8126f, 75.04426f,};
        float[] midTopLeftVertices = {435.37506f, 461.593f, 435.93756f, 440.354f, 459.00006f, 439.6461f, 457.87506f, 463.0089f};        
        float[] midTopRightVertices = {622.68756f, 461.593f, 622.12506f, 439.6461f, 646.87506f, 439.6461f, 647.43756f, 462.30093f};        
        float[] midBottomLeftVertices = {435.37506f, 288.14163f, 436.50006f, 266.19473f, 459.00006f, 266.19473f, 459.56256f, 287.43365f};
        float[] midBottomRightVertices = {622.68756f, 288.14163f, 622.12506f, 265.48676f, 646.87506f, 266.19473f, 646.87506f, 288.14163f};  
        createBunkerSprite(squareBunker, 119.0753f, 640.47516f, 28.16f, 27.52f, topLeftVertices);
        createBunkerSprite(squareBunker, 913.5508f, 640.47516f, 28.16f, 27.52f, topRightVertices);
        createBunkerSprite(squareBunker, 120.96001f, 50.035347f,28.16f, 27.52f, bottomLeftVertices);      
        createBunkerSprite(squareBunker, 922.4863f, 50.035347f, 28.16f, 27.52f, bottomRightVertices);   
        createBunkerSprite(squareBunker, 433.96313f, 437.59723f, 28.16f, 27.52f, midTopLeftVertices);        
        createBunkerSprite(squareBunker, 621.0985f, 437.59723f, 28.16f, 27.52f, midTopRightVertices);
        createBunkerSprite(squareBunker, 433.96313f, 263.25092f, 28.16f, 27.52f, midBottomLeftVertices);        
        createBunkerSprite(squareBunker, 621.0985f, 263.25092f, 28.16f, 27.52f, midBottomRightVertices);     
    }
    
    protected void createX() {
        FileHandle x = Gdx.files.internal("bunkers/x.png");
        float[] vertices = {520.31256f, 395.7523f, 505.68756f, 381.593f, 523.12506f, 363.18585f, 506.81256f, 344.7788f,
        		521.43756f, 330.6195f, 539.43756f, 346.19473f, 556.87506f, 329.20358f, 573.18756f, 344.7788f, 556.31256f, 361.76996f,
        		572.62506f, 380.885f, 558.00006f, 395.7523f, 540.00006f, 379.46906f};
        createBunkerSprite(x, 505.5187f, 329.31393f, 67.84f, 67.84f, vertices);
    }
    protected void createLongRectangle() {
    	FileHandle longRectangle = Gdx.files.internal("bunkers/longRectangle.png");
    	float[] vertices = {532.12506f, 109.73453f, 532.68756f, 70.08851f, 546.18756f, 70.796455f, 545.62506f, 109.02658f};
    	createBunkerSprite(longRectangle, 532.37506f, 70.08851f, 14f, 42f, vertices);
    	
    }
    protected void createDeadBox() {
    	FileHandle deadBox = Gdx.files.internal("misc/deadbox.png");
    	float[] leftDeadBox = {19.125002f, 415.57532f, 19.125002f, 304.4248f, 57.93754f, 305.13278f, 58.500008f, 414.86734f};
        float[] rightDeadBox = {1020.3751f, 414.86734f, 1021.5001f, 304.4248f, 1059.7501f, 305.13278f, 1059.7501f, 414.15936f};
        createBunkerSprite(deadBox, 19.125f, 303.628f, 40f, 112f, leftDeadBox);
        createBunkerSprite(deadBox, 1020.625f, 303.628f, 40f, 112f, rightDeadBox);
    }
    protected ArrayList<Bunker> getBunkers(){
        return this.bunkers;
    }
    
}

package com.speedball.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.speedball.game.*;

public class SpeedballServer
{
    private static final float MAX_X = 1045f;
    private static final float MAX_Y = 690f;

    //private static Game game = new Game();
    private static Utils utils = new Utils();
    private static GunUtils gunUtils = new GunUtils();
    private static ArrayList<PlayerThread> threads = new ArrayList<PlayerThread>();
    private static ArrayList<String> inputs = new ArrayList<String>();
    private static ArrayList<Player> players = new ArrayList<Player>();
    public static volatile int playerCounter = 0;
    public static long currTime = System.currentTimeMillis();
    public static long deltaTime = 0;
    private static PaintballMap pbMap = new PaintballMap(true);
    //private static boolean shotOnce = false;
    private static int counter = 0;
    public static volatile int hitPlayer = -1;


    public static void main(String[] args) throws IOException {
        //String clientSentence;
        //String captializedSentence;
        pbMap.createBunkers();
        ServerSocket listeningSocket = new ServerSocket(6789);

        //ArrayList<Socket> connectionSockets = new ArrayList<Socket>();

        //listen and connect
        System.out.println("Server Started!");
        //Socket connectionSocket = listeningSocket.accept();
        Socket connectionSocket = null;


        while (threads.size() < 2) {
            try {
                connectionSocket = listeningSocket.accept(); 
            }catch (IOException e) {
                System.out.println("I/O error");
            }
            PlayerThread pt = new PlayerThread(connectionSocket);
            threads.add(pt);
            inputs.add("");
            //TODO: Player's initial x y needs to be calculated for dead box
            if (playerCounter == 0) {
                players.add(new Player(1030f, 350f));
            }
            else {
                players.add(new Player(25f, 352f));
            }
            playerCounter++;
            pt.start();
        }
        while (threads.size() > 0) {
            for (int i = 0; i < threads.size(); i++) {
                PlayerThread thread = threads.get(i);
                thread.isDone = false;
                inputs.set(i,  thread.data);
            }

            for (int i = 0; i < inputs.size(); i++) {
                processData(inputs.get(i), players.get(i), i);
            }

            deltaTime = System.currentTimeMillis() - currTime;
            currTime = System.currentTimeMillis();
            //TODO: Projectile stuff
            String tmp = createClientString();

            for (int i = 0; i < threads.size(); i++) {
                PlayerThread thread = threads.get(i);
                thread.temp = tmp;
                thread.isDone = true;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private static String createClientString() {
        String clientString = "";
        //System.out.println("newGameStarted: " + newGameStarted);
        for (int i = 0; i < players.size(); i++) {
            clientString += "p" + (i + 1) + " ";
            if (utils.playerInBounds(players.get(i).getPlayerX(), players.get(i).getPlayerY(), MAX_X, MAX_Y)) {
                utils.playerMtvLogic(players.get(i), pbMap);
                clientString += players.get(i).getPlayerX() + " ";
                clientString += players.get(i).getPlayerY() + " ";
            }
            else {
                clientString += utils.resetPlayerAtXBound(players.get(i).getPlayerX(), MAX_X) + " ";
                clientString += utils.resetPlayerAtYBound(players.get(i).getPlayerY(), MAX_Y) + " ";
            }
            clientString += players.get(i).getMouseAngle() + " ";
            //System.out.println("Num paintballs: " + players.get(i).getPaintballs().size());

            if (i == 0) {
                clientString = gunUtils.addPlayerPaintballs(players.get(i).getPaintballs(), pbMap.getBunkers(), players.get(1), clientString, i);
            }
            else if(i == 1) {
                clientString = gunUtils.addPlayerPaintballs(players.get(i).getPaintballs(), pbMap.getBunkers(), players.get(0), clientString, i);
            }

            if (players.get(i).isHit()) {
                hitPlayer = i;
            }
        }
        return clientString;
    }

    private static void processData(String input, Player player, int index) {
        String[] splitString = input.split("\\s+");

        int x = 0;
        int y = 0;
        String mouseAngle = "";
        String gunX = "";
        String gunY = "";
        String slope = "";
        String quadrant = "";
        float playerSpeed = 1f;
        int click = 0;
        // TODO: Projectile when clicked
        //System.out.println("String to process: " + input);
        for (int i = 0; i < splitString.length; i++) {
            //System.out.println("Current Char:" + s);
            switch (splitString[i]) {
                case "W":
                    y += 1;
                    break;
                case "A":
                    x -= 1;
                    break;
                case "S":
                    y -= 1;
                    break;
                case "D":
                    x += 1;
                    break;
                case "~":
                    slope = splitString[i + 1];
                    quadrant = splitString[i + 2];
                    gunX = splitString[i + 3];
                    gunY = splitString[i + 4];
                    break;
                case "-":
                    playerSpeed = 1.3f;
                    break;
                case "`":
                    //System.out.println("From client: " +  input + "Length: " + splitString.length);
                    //System.out.println("` newGame clicked!!!");
                    hitPlayer = -1;
                    //player.setPaintballs(new ArrayList<Paintball>());
                    if (index == 0) {
                        player.setPlayerX(1030f);
                        player.setPlayerY(350f);  
                    }
                    else {
                        player.setPlayerX(25f);
                        player.setPlayerY(352f);
                    }
                    player.setPaintballs(new ArrayList<Paintball>());
                    player.setHit(false);
                    player.setPaintballCounter(-1);     
                default: 
                    mouseAngle = splitString[i];
            }
        }
        //System.out.println("Counter: " + counter);

        if (x != 0 && y != 0) {
            player.setPlayerX(player.getPlayerX() + (float)(x * 0.7 * playerSpeed));
            player.setPlayerY(player.getPlayerY() + (float)(y * 0.7 * playerSpeed));
        }
        else {
            if (x != 0 || y != 0) {
                player.setPlayerX(player.getPlayerX() + x * playerSpeed);
                player.setPlayerY(player.getPlayerY() + y * playerSpeed);
            }
        }
        if (mouseAngle != "") {
            player.setMouseAngle(Float.parseFloat(mouseAngle));
        }
        if (slope != "" && quadrant != "") {
            //System.out.println("Slope: " + slope + " Quadrant: " + quadrant);
            player.addPaintball(new Paintball(Float.parseFloat(slope), Integer.parseInt(quadrant)));
            player.incrementPaintballCounter();
            player.getPaintballs().get(player.getPaintballCounter()).setBounds(Float.parseFloat(gunX), Float.parseFloat(gunY), 4, 4);
            player.setGunX(Float.parseFloat(gunX));
            player.setGunY(Float.parseFloat(gunY));
            slope = "";
            quadrant = "";
            //shotOnce = true;
            //System.out.println("Paintball Counter Var: " + player.getPaintballCounter());
        }
    }
}

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
	protected static ArrayList<PlayerThread> threads = new ArrayList<PlayerThread>();
	protected static ArrayList<String> inputs = new ArrayList<String>();
	protected static ArrayList<Player> players = new ArrayList<Player>();
	public static long currTime = System.currentTimeMillis();
	public static long deltaTime = 0;

    
    public static void main(String[] args) throws IOException {
        //String clientSentence;
        //String captializedSentence;
        ServerSocket listeningSocket = new ServerSocket(6789);
        
        //ArrayList<Socket> connectionSockets = new ArrayList<Socket>();
        
        //listen and connect
        System.out.println("Server Started!");
        //Socket connectionSocket = listeningSocket.accept();
        Socket connectionSocket = null;
        
        
        while (threads.size() < 1) {
            try {
                connectionSocket = listeningSocket.accept(); 
            }catch (IOException e) {
                System.out.println("I/O error");
            }
            PlayerThread pt = new PlayerThread(connectionSocket);
            threads.add(pt);
            inputs.add("");
            //TODO: Player's initial x y needs to be calculated for dead box
            players.add(new Player(0f, 0f));
            pt.start();
            //game.addPlayer();
        }
        while (threads.size() > 0) {
	        	for (int i = 0; i < threads.size(); i++) {
	        		PlayerThread thread = threads.get(i);
	        		thread.isDone = false;
	        		//System.out.println(thread.data);
	        		inputs.set(i,  thread.data);
	        	}
	        	for (int i = 0; i < inputs.size(); i++) {
	        		//System.out.println("Input String: " + inputs.get(i));
        			//System.out.println("Before: " + players.get(i).getPlayerX());
        			processData(inputs.get(i), players.get(i));
        			//System.out.println("After: " + players.get(i).getPlayerX());
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
		for (int i = 0; i < players.size(); i++) {
			//System.out.println(players.get(i).getPlayerX() + " " + players.get(i).getPlayerY());
			clientString += "p ";
			if (utils.playerInBounds(players.get(i).getPlayerX(), players.get(i).getPlayerY(), MAX_X, MAX_Y)) {
				clientString += players.get(i).getPlayerX() + " ";
				clientString += players.get(i).getPlayerY() + " ";
			}
			else {
				clientString += utils.resetPlayerAtXBound(players.get(i).getPlayerX(), MAX_X) + " ";
				clientString += utils.resetPlayerAtXBound(players.get(i).getPlayerY(), MAX_Y) + " ";
			}
			clientString += players.get(i).getMouseAngle();
		}
		//System.out.println("Client String: " + clientString);
		return clientString;
	}


	private static void processData(String input, Player player) {
		String[] splitString = input.split("\\s+");
		int x = 0;
		int y = 0;
		float mouseAngle = 0f;
		int click = 0;
		// TODO: Projectile when clicked
		System.out.println("String to process: " + input);
		for (String s: splitString) {
			System.out.println("Current Char:" + s);
			switch (s) {
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
				click = 1;
				break;
			}
//			else {
//				mouseAngle = Float.parseFloat(character);
//			}
		}
		if (x != 0 || y != 0) {
			player.setPlayerX(player.getPlayerX() + x);
			player.setPlayerY(player.getPlayerY() + y);
//			player.move((float) Math.atan2((double)y, (double)x));
		}
		player.setMouseAngle(mouseAngle);
	}
}
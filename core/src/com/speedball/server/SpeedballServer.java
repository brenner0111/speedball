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
        		inputs.set(i,  thread.data);
        	}
        	for (int i = 0; i < inputs.size(); i++) {
        		System.out.println("Input String: " + inputs.get(i));
        		processData(inputs.get(i), players.get(i));
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
    
        //set up streams 
        //BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        //DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

        // read from client
        //clientSentence = inFromClient.readLine();

        // prepare for client
        //captializedSentence = clientSentence.toUpperCase() + '\n';
        //outToClient.writeBytes(captializedSentence);

        //listeningSocket.close();
    }


	private static String createClientString() {
		String clientString = "";
		for (Player player: players) {
			clientString += "p ";
			if (utils.playerInBounds(player.getPlayerX(), player.getPlayerY(), MAX_X, MAX_Y)) {
				clientString += player.getPlayerX() + " ";
				clientString += player.getPlayerY() + " ";
			}
			else {
				clientString += utils.resetPlayerAtXBound(player.getPlayerX(), MAX_X);
				clientString += utils.resetPlayerAtXBound(player.getPlayerY(), MAX_Y);
			}
			clientString += player.getMouseAngle();
		}
		System.out.println("Client String: " + clientString);
		return clientString;
	}


	private static void processData(String input, Player player) {
		String[] splitString = input.split("\\s+");
		int x = 0;
		int y = 0;
		float mouseAngle = 0f;
		// TODO: Projectile when clicked
		for (String character: splitString) {
			if (character == "W") {
				y += 1;
				break;
			}
			else if (character == "A") {
				x -= 1;
				break;
			}
			else if (character == "S") {
				y -= 1;
				break;
			}
			else if (character == "D") {
				x += 1;
				break;
			}
			else if (character == "~") {
				//TODO: Implement projectile stuff here
			}
			else if (character != "") {
				mouseAngle = Float.parseFloat(character);
			}
		}
		if (x != 0 || y != 0) {
			player.setPlayerX(x);
			player.setPlayerY(y);
		}
		player.setMouseAngle(mouseAngle);
	}
}

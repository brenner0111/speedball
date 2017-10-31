package com.speedball.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SpeedballServer
{
    
    public static void main(String[] args) throws IOException {
        Game game = new Game();
        //String clientSentence;
        //String captializedSentence;
        ServerSocket listeningSocket = new ServerSocket(6789);
        
        //ArrayList<Socket> connectionSockets = new ArrayList<Socket>();
        
        //listen and connect
        System.out.println("Server Started!");
        //Socket connectionSocket = listeningSocket.accept();
        Socket connectionSocket = null;
        
        
        while (game.getNumPlayers() < 2) {
            try {
                connectionSocket = listeningSocket.accept(); 
            }catch (IOException e) {
                System.out.println("I/O error");
            }
            new PlayerThread(connectionSocket).start();
            game.addPlayer();
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
}

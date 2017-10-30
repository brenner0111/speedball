package com.speedball.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SpeedballClient
{
    public static void main(String[] args) throws UnknownHostException, IOException {
        //sets the socket to the local address 
        //Socket clientSocket = new Socket(InetAddress.getLocalHost().getHostAddress(), 6789);
        Socket clientSocket = new Socket("34.207.157.240", 6789);
        
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
    }
}

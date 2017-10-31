package com.speedball.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class PlayerThread extends Thread
{
    protected Socket socket;

    public PlayerThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream inFromClient = null;
        BufferedReader buffer = null;
        DataOutputStream outToClient = null;
        
        try {
            inFromClient = socket.getInputStream();
            buffer = new BufferedReader(new InputStreamReader(inFromClient));
            outToClient = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                line = buffer.readLine();
                line = line.toUpperCase() + '\n';
                outToClient.writeBytes(line);
                System.out.println(line);
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}

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
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        String lineFromServer;
        while (true) {
            try {
                line = brinp.readLine();
                lineFromServer = line.toUpperCase() + '\n';
                out.writeBytes(lineFromServer);
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}

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
    protected String data;
    protected boolean isDone;
    protected String outString;
    protected volatile String temp;
    protected volatile int num;
    protected volatile int hitPlayer;

    public PlayerThread(Socket clientSocket) {
        this.socket = clientSocket;
        this.data = "";
        this.isDone = false;
        this.outString = "";
        this.num = SpeedballServer.playerCounter;
        this.hitPlayer = SpeedballServer.hitPlayer;
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
        //Eventually need to change while game hasn't started
        while (true) {
            hitPlayer = SpeedballServer.hitPlayer;
            try {
                data = brinp.readLine();
                if (isDone) {
                    outString = temp.substring(0, temp.length());
                }
                out.writeBytes(num + " " + hitPlayer + " " + outString + '\n');
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}

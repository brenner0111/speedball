package com.speedball.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.speedball.game.*;

public class ClientNetworkThread extends Thread {
	private Socket clientSocket;
	private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    public volatile String fromServer = "";
    private boolean connectedToServer = false;
    

	public void run() {
		if (!connectedToServer) {
			connectToServer();
			connectedToServer = true;
		}
		sendAndReceiveFromServer();
	}

	private void connectToServer() {
		try {
			clientSocket = new Socket("localhost", 6789);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("Connected to Server");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	private void sendAndReceiveFromServer() {
		while(true) {
			try {
				//Send to server - KEY PRESSES
				outToServer.writeBytes(getKeyboardInputs() + "\n");
				//receive from server - RECEIVE STRING FROM SERVER
				fromServer = inFromServer.readLine();

				//System.out.println(fromServer);

			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private String getKeyboardInputs() {
		String ret = "";
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			ret += "W ";
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			ret += "A ";
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			ret += "S ";
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			ret += "D ";
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			ret += "- ";
		}
		if (Gdx.input.justTouched()) {
			ret +="~ " + SpeedballClient.paintballInfo + " ";
		}
		ret += " " + SpeedballClient.mouseAngle + " ";
		return ret;
	}
}
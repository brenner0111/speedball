package com.speedball.server;

public class Game {

	private boolean gameHasStarted;
	private int numPlayers;

	public Game() {
		gameHasStarted = false;
		numPlayers = 0;
	}
	
	public boolean getGameHasStarted() {
		return gameHasStarted;
	}
	public void setGameHasStarted(boolean started) {
		gameHasStarted = started;
	}
	public int getNumPlayers() {
		return numPlayers;
	}
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	public void addPlayer() {
		numPlayers++;
	}
}

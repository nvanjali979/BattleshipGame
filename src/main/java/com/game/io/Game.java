package com.game.io;

public class Game {

	private Player playerOne;
	private Player playerTwo;
	private String status;

	public Game() {
		playerOne = new Player("playerOne"); // remove name if not needed
		playerTwo = new Player("playerTwo");
	}

	public Player getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

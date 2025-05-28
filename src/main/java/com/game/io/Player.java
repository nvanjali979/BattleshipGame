package com.game.io;

/**
 * Player class hold information about the player such as Player Name 
 * and the board details
 */
public class Player {

	private Board board;
	private String name;

	public Player(String name) {
		this.name = name;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

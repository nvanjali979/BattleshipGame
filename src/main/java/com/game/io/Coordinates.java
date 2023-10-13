package com.game.io;

/**
 * Holds information about the Board Coordinate
 */
public class Coordinates {

	private char row;
	private int col;

	public char getRow() {
		return row;
	}

	public void setRow(char row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Coordinates(char row, int col) {
		this.row = row;
		this.col = col;
	}
}

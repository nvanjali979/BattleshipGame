package com.game.io;

import java.util.List;

/**
 * Ship details in the request are mapped to this class
 */
public class Ship {

	private String name;
	private int size;
	private char dir;
	private char row;
	private int col;
	private int shipsLeft;
	private List<Coordinates> coordinatesList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public char getDir() {
		return dir;
	}

	public void setDir(char dir) {
		this.dir = dir;
	}

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

	public int getShipsLeft() {
		return shipsLeft;
	}

	public void setShipsLeft(int shipsLeft) {
		this.shipsLeft = shipsLeft;
	}

	public List<Coordinates> getCoordinatesList() {
		return coordinatesList;
	}

	public void setCoordinatesList(List<Coordinates> coordinatesList) {
		this.coordinatesList = coordinatesList;
	}

	public Ship(String name, int size, char dir, char row, int col) {
		super();
		this.name = name;
		this.size = size;
		this.dir = dir;
		this.row = row;
		this.col = col;
	}

}

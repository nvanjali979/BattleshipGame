package com.game.io;

import java.util.List;

public class Ship {

	private String name;
	private int size;
	private String dir;
	private int row;
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

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
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

}

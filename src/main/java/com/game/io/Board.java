package com.game.io;

import java.util.List;

public class Board {

	private int size;
	private String[][] grid; // X/SHIPNAME/HIT/MISS/SUNK
	private List<Ship> listofShips;
	

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String[][] getGrid() {
		return grid;
	}

	public void setGrid(String[][] grid) {
		this.grid = grid;
	}

	public Board(int size) {
		grid = new String[size][size];
	}

	public List<Ship> getListofShips() {
		return listofShips;
	}

	public void setListofShips(List<Ship> listofShips) {
		this.listofShips = listofShips;
	}

}

package com.game.io;

import java.util.List;

/**
 * Description : Board class is defined for each player
 * It has grid object which holds a 2d array of string
 * to denote if it can contain a default value X, ship name or the ship Status 
 */
public class Board {

	private String[][] grid; // X/SHIPNAME/HIT/MISS/SUNK
	private List<Ship> listofShips;

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

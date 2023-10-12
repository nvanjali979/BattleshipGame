package com.game.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {

	private Board board;
	private static final int BOARD_SIZE = 10;

	@BeforeEach
	public void setUp() {
		board = new Board(BOARD_SIZE);
	}

	@Test
	public void testGetGrid() {
		String[][] grid = board.getGrid();
		assertNotNull(grid);
		assertEquals(grid.length, BOARD_SIZE);
	}

	@Test
	public void testSetGrid() {
		String[][] grid = new String[BOARD_SIZE][];
		board.setGrid(grid);
		assertEquals(board.getGrid(), grid);
	}

	@Test
	public void testListofShips() {
		board.setListofShips(getSampleShips());
		assertNotNull(board.getListofShips());
		assertEquals(board.getListofShips().size(), 2);
	}

	private List<Ship> getSampleShips() {
		List<Ship> ships = new ArrayList<>();
		ships.add(new Ship("Ship1", 3, 'H', 'H', 6));
		ships.add(new Ship("Ship2", 2, 'V', 'B', 1));
		return ships;
	}
}

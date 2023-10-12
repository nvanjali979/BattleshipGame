package com.game.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {

	private Player player;
	private Board board;

	@BeforeEach
	public void setUp() {
		player = new Player("PlayerOne");
		board = new Board(10); // Create a board with a size of 10
	}

	@Test
	public void testGetBoard() {
		assertNull(player.getBoard());
	}

	@Test
	public void testSetBoard() {
		player.setBoard(board);
		assertNotNull(player.getBoard());
		assertSame(board, player.getBoard());
	}

	@Test
	public void testGetName() {
		assertEquals("PlayerOne", player.getName());
	}

	@Test
	public void testSetName() {
		player.setName("NewPlayerName");

		assertEquals("NewPlayerName", player.getName());
	}
}
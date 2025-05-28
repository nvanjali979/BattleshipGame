package com.game.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {

	private Game game;

	@BeforeEach
	public void setUp() {
		game = new Game();
	}

	@Test
	public void testGetPlayerOne() {
		assertNotNull(game.getPlayerOne());
		assertEquals("playerOne", game.getPlayerOne().getName());
	}

	@Test
	public void testSetPlayerOne() {
		Player newPlayer = new Player("NewPlayer");
		game.setPlayerOne(newPlayer);

		assertNotNull(game.getPlayerOne());
		assertEquals("NewPlayer", game.getPlayerOne().getName());
	}

	@Test
	public void testGetPlayerTwo() {
		assertNotNull(game.getPlayerTwo());
		assertEquals("playerTwo", game.getPlayerTwo().getName());
	}

	@Test
	public void testSetPlayerTwo() {
		Player newPlayer = new Player("AnotherPlayer");
		game.setPlayerTwo(newPlayer);

		assertNotNull(game.getPlayerTwo());
		assertEquals("AnotherPlayer", game.getPlayerTwo().getName());
	}
}

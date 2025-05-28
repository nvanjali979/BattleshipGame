package com.game.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoordinatesTest {

	private Coordinates coordinates;

	@BeforeEach
	public void setUp() {
		coordinates = new Coordinates('B', 5);
	}

	@Test
	public void testGetCol() {
		assertEquals(5, coordinates.getCol());
	}

	@Test
	public void testSetCol() {
		coordinates.setCol(2);
		assertEquals(2, coordinates.getCol());
	}

	@Test
	public void testGetRow() {
		assertEquals('B', coordinates.getRow());
	}

	@Test
	public void testSetRow() {
		coordinates.setRow('B');
		assertEquals('B', coordinates.getRow());
	}
}

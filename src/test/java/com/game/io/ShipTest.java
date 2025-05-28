package com.game.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShipTest {

	private Ship ship;
	private List<Coordinates> coordinatesList;

	@BeforeEach
	public void setUp() {
		ship = new Ship("Cruiser", 3, 'H', 'A', 1);
		coordinatesList = new ArrayList<>();
		coordinatesList.add(new Coordinates('A', 0));
		coordinatesList.add(new Coordinates('A', 1));
		coordinatesList.add(new Coordinates('A', 2));
		ship.setCoordinatesList(coordinatesList);
	}

	@Test
	public void testGetName() {
		assertEquals("Cruiser", ship.getName());
	}

	@Test
	public void testSetName() {
		ship.setName("Submarine");
		assertEquals("Submarine", ship.getName());
	}

	@Test
	public void testGetSize() {
		assertEquals(3, ship.getSize());
	}

	@Test
	public void testSetSize() {
		ship.setSize(4);
		assertEquals(4, ship.getSize());
	}

	@Test
	public void testGetDir() {
		assertEquals('H', ship.getDir());
	}

	@Test
	public void testSetDir() {
		ship.setDir('V');
		assertEquals('V', ship.getDir());
	}

	@Test
	public void testGetRow() {
		assertEquals('A', ship.getRow());
	}

	@Test
	public void testSetRow() {
		ship.setRow('B');
		assertEquals('B', ship.getRow());
	}

	@Test
	public void testGetCol() {
		assertEquals(1, ship.getCol());
	}

	@Test
	public void testSetCol() {
		ship.setCol(1);
		assertEquals(1, ship.getCol());
	}

}

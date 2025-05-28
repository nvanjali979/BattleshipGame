package com.game.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.game.exceptions.GameException;
import com.game.io.Board;
import com.game.io.Coordinates;
import com.game.io.Ship;
import com.game.service.impl.GameStartServiceImpl;

public class GameStartServiceImplTest {

	private GameStartService gameStartService;
	private Board board;

	@BeforeEach
	public void setUp() {
		gameStartService = new GameStartServiceImpl();
		board = mock(Board.class);
	}

	@Test
    public void testPlaceShipsValid() throws GameException {
    	when(board.getGrid()).thenReturn(new String[10][10]);
        assertTrue(gameStartService.begin(getSampleShips(), "playerOne"));
    }

	@Test
    public void testPlaceShipsInvalid() throws GameException {
    	when(board.getGrid()).thenReturn(new String[10][10]);
        assertThatExceptionOfType(GameException.class)
        .isThrownBy(() -> gameStartService.begin(getInvalidShips(), "playerTwo"));
    }

	@Test
    public void testPlaceShipsOverlappingInvalid() throws GameException {
    	when(board.getGrid()).thenReturn(new String[10][10]);
        assertThatExceptionOfType(GameException.class)
        .isThrownBy(() -> gameStartService.begin(getOverlappingShips(), "playerTwo"));
    }

	@Test
	public void testShootBoardHit() throws GameException {
		gameStartService.begin(getSampleShips(), "playerOne");
		String result = gameStartService.shootBoard("playerTwo", new Coordinates('H', 6));
		assertEquals("Good!! You hit it!!", result);
	}

	@Test
	public void testShootBoardInvalidCoordinate() throws GameException {
		gameStartService.begin(getSampleShips(), "playerOne");
		assertThatExceptionOfType(GameException.class)
				.isThrownBy(() -> gameStartService.shootBoard("playerTwo", new Coordinates('K', 1)));
	}

	@Test
	public void testShootBoardMiss() throws GameException {
		gameStartService.begin(getSampleShips(), "playerOne");
		String result = gameStartService.shootBoard("playerTwo", new Coordinates('A', 0));
		assertEquals("You missed it !! Try again !!", result);
	}

	@Test
	public void testShootBoardSunk() throws GameException {
		gameStartService.begin(getSampleShips(), "playerOne");
		gameStartService.shootBoard("playerTwo", new Coordinates('B', 1));
		String result = gameStartService.shootBoard("playerTwo", new Coordinates('C', 1));
		assertEquals("SUNK!!", result.split(" ")[1]);
	}

	@Test
	public void testShootBoardHitRepeat() throws GameException {
		gameStartService.begin(getSampleShips(), "playerOne");
		gameStartService.shootBoard("playerTwo", new Coordinates('B', 1));
		assertThatExceptionOfType(GameException.class)
				.isThrownBy(() -> gameStartService.shootBoard("playerTwo", new Coordinates('B', 1)));
	}

	@Test
	public void testShootBoardHitAfterSunk() throws GameException {
		gameStartService.begin(getSampleShips(), "playerOne");
		gameStartService.shootBoard("playerTwo", new Coordinates('B', 1));
		gameStartService.shootBoard("playerTwo", new Coordinates('C', 1));
		assertThatExceptionOfType(GameException.class)
				.isThrownBy(() -> gameStartService.shootBoard("playerTwo", new Coordinates('B', 1)));
	}

	@Test
	public void testShootBoardWin() throws GameException {
		gameStartService.begin(getSampleShips(), "playerTwo");
		gameStartService.shootBoard("playerOne", new Coordinates('H', 6));
		gameStartService.shootBoard("playerOne", new Coordinates('B', 1));
		gameStartService.shootBoard("playerOne", new Coordinates('H', 7));
		gameStartService.shootBoard("playerOne", new Coordinates('H', 8));
		String result = gameStartService.shootBoard("playerOne", new Coordinates('C', 1));
		assertTrue(result.contains("WON"));
	}

	@Test
	public void testShootBoardAfterWin() throws GameException {
		gameStartService.begin(getSampleShips(), "playerTwo");
		gameStartService.shootBoard("playerOne", new Coordinates('H', 6));
		gameStartService.shootBoard("playerOne", new Coordinates('B', 1));
		gameStartService.shootBoard("playerOne", new Coordinates('H', 7));
		gameStartService.shootBoard("playerOne", new Coordinates('H', 8));
		gameStartService.shootBoard("playerOne", new Coordinates('C', 1));
		assertThatExceptionOfType(GameException.class)
				.isThrownBy(() -> gameStartService.shootBoard("playerOne", new Coordinates('H', 8)));
	}

	@Test
	public void testShootBoardInvalid() throws GameException {
		assertThatExceptionOfType(GameException.class)
				.isThrownBy(() -> gameStartService.shootBoard("playerOne", new Coordinates('H', 8)));
	}

	private List<Ship> getSampleShips() {
		List<Ship> ships = new ArrayList<>();
		ships.add(new Ship("Submarine", 3, 'H', 'H', 6));
		ships.add(new Ship("Destroyer", 2, 'V', 'B', 1));
		return ships;
	}

	private List<Ship> getOverlappingShips() {
		List<Ship> ships = new ArrayList<>();
		ships.add(new Ship("Submarine", 3, 'H', 'C', 0));
		ships.add(new Ship("Destroyer", 2, 'V', 'B', 1));
		return ships;
	}

	private List<Ship> getInvalidShips() {
		List<Ship> ships = new ArrayList<>();
		ships.add(new Ship("Submarine", 3, 'H', 'K', 0)); // Invalid ship
		ships.add(new Ship("Destroyer", 2, 'H', 'B', 0));
		return ships;
	}

}

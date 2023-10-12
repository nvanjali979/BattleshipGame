package com.game.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.game.exceptions.GameException;
import com.game.io.Board;
import com.game.io.Coordinates;
import com.game.io.Game;
import com.game.io.Player;
import com.game.io.Ship;
import com.game.service.GameStartService;

public class GameStartServiceImpl implements GameStartService {

	private static final int BOARD_SIZE = 10;
	private static final String X = "X";
	private static final String MISS = "MISS";
	private static final String HIT = "HIT";
	private static final String SUNK = "SUNK";
	private static final String GAMEOVER = "GAMEOVER";
	private List<Coordinates> coordinateList;
	private Game game = new Game();

	public Boolean begin(List<Ship> ships, String playerName) throws GameException {
		Player player = setPlayer(playerName);
		resetBoard(player);
		return placeShips(player, ships);
	}

	private Player setPlayer(String playerName) {
		Player player;
		if (playerName.equalsIgnoreCase("playerOne"))
			player = game.getPlayerOne();
		else
			player = game.getPlayerTwo();
		return player;
	}

	private void resetBoard(Player player) {
		Board board = new Board(BOARD_SIZE);
		for (String[] grid : board.getGrid()) {
			Arrays.fill(grid, "X");
		}
		player.setBoard(board);
	}

	private Boolean placeShips(Player player, List<Ship> ships) throws GameException {
		List<Ship> validShips = new ArrayList<Ship>();
		for (Ship ship : ships) {
			if (isShipValid(player, ship)) {
				validShips.add(ship);
			} else {
				throw new GameException("Invalid coordinate for " + ship.getName());
			}
		}
		player.getBoard().setListofShips(validShips);
		return true;
	}

	private Boolean isShipValid(Player player, Ship ship) throws GameException {

		if (!shipSizeExceeded(ship)) {
			if (!isOverlapping(player, ship)) {
				setGridDetails(player, ship);
				return true;
			}
		}
		return false;
	}

	private Boolean shipSizeExceeded(Ship ship) {
		int y = ship.getCol();
		int x = letterToNumberConversion(ship.getRow());
		int shipSize = ship.getSize();

		if (checkIfInvalidCoordinate(x, y))
			return true;

		if (ship.getDir() == 'H')
			return (y + shipSize > BOARD_SIZE);
		else
			return (x + shipSize > BOARD_SIZE);
	}

	private Boolean isOverlapping(Player player, Ship ship) {
		int y = ship.getCol();
		int x = letterToNumberConversion(ship.getRow());

		for (int i = 0; i < ship.getSize(); i++) {

			if (player.getBoard().getGrid()[x][y] != "X")
				return true;

			if (ship.getDir() == 'H') {
				y++;
			} else if (ship.getDir() == 'V') {
				x++;
			}
		}

		return false;
	}

	private void setGridDetails(Player player, Ship ship) {

		coordinateList = new ArrayList<Coordinates>();
		int y = ship.getCol();
		int x = letterToNumberConversion(ship.getRow());
		
		for (int i = 0; i < ship.getSize(); i++) {
			player.getBoard().getGrid()[x][y] = ship.getName();
			getListofCoodinates( x, y);
			if (ship.getDir() == 'H') {
				y++;
			} else if (ship.getDir() == 'V') {
				x++;
			}

		}
		setShipDetails(ship);
	}

	private void setShipDetails(Ship ship) {
		ship.setCoordinatesList(coordinateList);
		ship.setShipsLeft(ship.getSize());
	}

	private void getListofCoodinates(int x, int y) {
		coordinateList.add(new Coordinates(numberToLetterConversion(x), y));
	}

	public String shootBoard(String playerName, Coordinates cordinates) throws GameException {
		Player player = null, opponent = null;
		String message = null;

		int x = letterToNumberConversion(cordinates.getRow());
		int y = cordinates.getCol();

		if (game.getPlayerOne().getName().equalsIgnoreCase(playerName)) {
			player = game.getPlayerOne();
			opponent = game.getPlayerTwo();
		} else if (game.getPlayerTwo().getName().equalsIgnoreCase(playerName)) {
			opponent = game.getPlayerOne();
			player = game.getPlayerTwo();
		}

		if (checkIfInvalidCoordinate(x, y)) {
			throw new GameException("Invalid Coordinates");
		}

		if (hasShipsLeft(opponent) && game.getStatus()!=GAMEOVER) {
			String shipStatus = shoot(player, opponent, cordinates);

			if (shipStatus.contains(MISS)) {
				message = "You missed it !! Try again !!";
			} else if (shipStatus.contains(HIT)) {
				message = "Good!! You hit it!!";
			} else if (shipStatus.contains(SUNK)) {
				if (!hasShipsLeft(opponent)) {
					game.setStatus(GAMEOVER);
					message = "CONGRATS!!! " + player.getName() + ". YOU WON !!!";
				} else {
					message = shipStatus.concat("!! Awesome!!");
				}
			}
		} else {
			throw new GameException("Game over");
		}
		return message;
	}

	private Boolean checkIfInvalidCoordinate(int x, int y) {
		return (x >= BOARD_SIZE || y >= BOARD_SIZE);
	}

	private Boolean hasShipsLeft(Player player) throws GameException {
		if (null != player.getBoard() && !player.getBoard().getListofShips().isEmpty()) {
			if (player.getBoard().getListofShips().stream().anyMatch(ship -> ship.getShipsLeft() > 0)) {
				return true;
			}
		} else {
			throw new GameException("No ships were placed");
		}
		return false;
	}

	private int letterToNumberConversion(char ch) {
		return ch - 'A';
	}

	private char numberToLetterConversion(int i) {
		return (char) (i + 'A');
	}

	public String shoot(Player player, Player opponent, Coordinates cordinates) throws GameException {
		String shipStatus = null;
		int x = letterToNumberConversion(cordinates.getRow());
		int y = cordinates.getCol();
		String shipName = opponent.getBoard().getGrid()[x][y];
		if (X.equalsIgnoreCase(shipName)) {
			shipStatus = MISS;
		} else if (HIT.equalsIgnoreCase(shipName) || SUNK.equalsIgnoreCase(shipName)) {
			throw new GameException("Ship is already " + shipName);
		} else {
			int count = opponent.getBoard().getListofShips().stream()
					.filter(s -> s.getName().equalsIgnoreCase(shipName)).findAny().get().getShipsLeft();
			opponent.getBoard().getListofShips().stream().filter(s -> s.getName().equalsIgnoreCase(shipName)).findAny()
					.get().setShipsLeft(--count);

			if (count == 0) {
				for (Ship ship : opponent.getBoard().getListofShips()) {
					if (ship.getCoordinatesList().stream()
							.anyMatch(coordinate -> (coordinate.getRow() == cordinates.getRow()
									&& coordinate.getCol() == cordinates.getCol()))) {
						ship.getCoordinatesList().forEach(coordinate -> opponent.getBoard().getGrid()[x][y] = SUNK);
						shipStatus = ship.getName() + " " + SUNK;
					}
				}
			} else {
				opponent.getBoard().getGrid()[x][y] = HIT;
				shipStatus = HIT;
			}

		}
		return shipStatus;
	}

}

package com.game.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.game.exceptions.GameException;
import com.game.io.Board;
import com.game.io.Coordinates;
import com.game.io.Game;
import com.game.io.Player;
import com.game.io.Ship;

public class GameStartService {

	private static final int BOARD_SIZE = 10;
	private static final String X = "X";
	private static final String MISS = "MISS";
	private static final String HIT = "HIT";
	private static final String SUNK = "SUNK";
	private List<Coordinates> coordinateList;
	private Game game = new Game();

	public void begin(List<Ship> ships, String playerName) throws GameException {
		Player player = setPlayer(playerName);
		resetBoard(player);
		placeShips(player, ships);

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
		Boolean isValidShip = false;
		List<Ship> validShips = new ArrayList<Ship>();

		for (Ship ship : ships) {
			isValidShip = isShipValid(player, ship);

			if (isValidShip) {
				validShips.add(ship);
			} else {
				throw new GameException("Invalid coordinate for " + ship.getName());
			}
		}

		player.getBoard().setListofShips(validShips);
		return isValidShip;
	}

	private Boolean isShipValid(Player player, Ship ship) throws GameException {
		coordinateList = new ArrayList<Coordinates>();

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
		int x = ship.getRow();
		int shipSize = ship.getSize();

		if (x >= BOARD_SIZE || y >= BOARD_SIZE)
			return true;

		if (ship.getDir().equalsIgnoreCase("H"))
			return (y + shipSize > BOARD_SIZE);
		else
			return (x + shipSize > BOARD_SIZE);
	}

	private Boolean isOverlapping(Player player, Ship ship) {
		int y = ship.getCol();
		int x = ship.getRow();

		for (int i = 0; i < ship.getSize(); i++) {

			if (player.getBoard().getGrid()[x][y] != "X")
				return true;

			if (ship.getDir().equalsIgnoreCase("H")) {
				y++;
			} else if (ship.getDir().equalsIgnoreCase("V")) {
				x++;
			}
		}

		return false;
	}

	private void setGridDetails(Player player, Ship ship) {

		int y = ship.getCol();
		int x = ship.getRow();

		for (int i = 0; i < ship.getSize(); i++) {

			player.getBoard().getGrid()[x][y] = ship.getName();
			getListofCoodinates(coordinateList, x, y);
			if (ship.getDir().equalsIgnoreCase("H")) {
				y++;
			} else if (ship.getDir().equalsIgnoreCase("V")) {
				x++;
			}

		}
		setShipDetails(ship);
	}

	private void setShipDetails(Ship ship) {
		ship.setCoordinatesList(coordinateList);
		ship.setShipsLeft(ship.getSize());
	}

	private List<Coordinates> getListofCoodinates(List<Coordinates> coordinatesList, int x, int y) {
		coordinatesList.add(new Coordinates(x, y));
		return coordinatesList;
	}

	public String shootBoard(String playerName, Coordinates cordinates) throws GameException {
		Player player = null, opponent = null;
		String message = null;

		if (game.getPlayerOne().getName().equalsIgnoreCase(playerName)) {
			player = game.getPlayerOne();
			opponent = game.getPlayerTwo();
		} else if (game.getPlayerTwo().getName().equalsIgnoreCase(playerName)) {
			opponent = game.getPlayerOne();
			player = game.getPlayerTwo();
		}

		if (hasShipsLeft(opponent)) {
			String shipStatus = shoot(player, opponent, cordinates);
			if (shipStatus.contains(SUNK) && (!hasShipsLeft(opponent))) {
				message = "CONGRATS!!! " + player.getName() + ". YOU WON !!!";
			} else {
				message = shipStatus;
			}
		} else {
			throw new GameException("Game over");
		}
		return message;
	}

	public Boolean hasShipsLeft(Player player) throws GameException {
		if (null != player.getBoard()) {
			if (player.getBoard().getListofShips().stream().anyMatch(ship -> ship.getShipsLeft() > 0)) {
				return true;
			}
		} else {
			throw new GameException("No ships were placed");
		}
		return false;
	}

	public String shoot(Player player, Player opponent, Coordinates cordinates) throws GameException {
		String shipStatus = null;
		String shipName = opponent.getBoard().getGrid()[cordinates.getRow()][cordinates.getCol()];
		if (X.equalsIgnoreCase(shipName))
			shipStatus = MISS;
		else if (HIT.equalsIgnoreCase(shipName) || SUNK.equalsIgnoreCase(shipName)) {
			throw new GameException("Ship is already "+shipName);
		}
		else {
			int count = opponent.getBoard().getListofShips().stream()
					.filter(s -> s.getName().equalsIgnoreCase(shipName)).findAny().get().getShipsLeft();
			opponent.getBoard().getListofShips().stream().filter(s -> s.getName().equalsIgnoreCase(shipName)).findAny()
					.get().setShipsLeft(--count);

			if (count == 0) {
				for (Ship ship : opponent.getBoard().getListofShips()) {
					if (ship.getCoordinatesList().stream()
							.anyMatch(coordinate -> (coordinate.getRow() == cordinates.getRow()
									&& coordinate.getCol() == cordinates.getCol()))) {
						ship.getCoordinatesList()
								.forEach(coordinate -> opponent.getBoard().getGrid()[coordinate.getRow()][coordinate
										.getCol()] = SUNK);
						shipStatus = SUNK;
					}
				}
			} else if (count < 0) {
				throw new GameException("Ship is already SUNK ");
			} else {
				opponent.getBoard().getGrid()[cordinates.getRow()][cordinates.getCol()] = HIT;
				shipStatus = HIT;
			}

		}
		return shipStatus;
	}

}

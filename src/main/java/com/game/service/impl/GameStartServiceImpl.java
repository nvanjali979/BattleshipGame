package com.game.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.game.exceptions.GameException;
import com.game.io.Board;
import com.game.io.Coordinates;
import com.game.io.Game;
import com.game.io.Player;
import com.game.io.Ship;
import com.game.service.GameStartService;

/**
 * Description: This class contains methods to place ship, check for invalid coordinates and
 * overlapping coordinates, to check the shipStatus after shooting
 * 
 */
@Service
public class GameStartServiceImpl implements GameStartService {

	private static final int BOARD_SIZE = 10;
	private static final String GAMEOVER = "GAMEOVER";
	private List<Coordinates> coordinateList;
	private Game game = new Game();
	
	enum ShootStatus {
		X,    // default value
		MISS,
		HIT,
		SUNK
	}

	/**
	 * Gets player object
	 * Reset the board to default value X
	 * Place the ship on the board after checking if the coordinates are valid
	 */
	public Boolean begin(List<Ship> ships, String playerName) throws GameException {
		Player player = setPlayer(playerName);
		resetBoard(player);	
		return placeShips(player, ships);
	}

	/**
	 * Returns player object based on the player name 
	 */
	private Player setPlayer(String playerName) throws GameException {
		Player player = null;
		if(playerName.equalsIgnoreCase("playerOne")) {
			player = game.getPlayerOne();
		} else if(playerName.equalsIgnoreCase("playerTwo")) {
			player = game.getPlayerTwo();
		} else {
			throw new GameException("Invalid player name");
		}
		return player;
	}

	/**
	 * Resets the board grid value to X
	 * and sets to player object
	 */
	private void resetBoard(Player player) {
		Board board = new Board(BOARD_SIZE);
		for (String[] grid : board.getGrid()) {
			Arrays.fill(grid, "X");
		}
		player.setBoard(board);
	}

	/**
	 * Check if the ship is valid, 
	 * else throw Exception
	 */
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

	/**
	 * Checks if the ship coordinates are valid
	 * and sets the ship name to the grid 
	 */
	private Boolean isShipValid(Player player, Ship ship) throws GameException {

		if (!shipSizeExceeded(ship)) {
			if (!isOverlapping(player, ship)) {
				setGridDetails(player, ship);
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the ship size is exceeding
	 * the board size
	 */
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

	/**
	 * Checks if the ship is overlapping with 
	 * other ships
	 */
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

	/**
	 * Set the Grid details and the coordinateList
	 */
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

	/**
	 * Set the coordinateList to the ship object
	 * and set the no of ships left
	 */
	private void setShipDetails(Ship ship) {
		ship.setCoordinatesList(coordinateList);
		ship.setShipsLeft(ship.getSize());
	}

	/**
	 * Coordinates are added to the list for later retrieval
	 */
	private void getListofCoodinates(int x, int y) {
		coordinateList.add(new Coordinates(numberToLetterConversion(x), y));
	}

	/**
	 * Checks if the coordinates hit are valid and responds with the corresponding message 
	 * else throws an Exception
	 */
	public String shootBoard(String playerName, Coordinates cordinates) throws GameException {
		Player opponent = null;
		String message = null;

		int x = letterToNumberConversion(cordinates.getRow());
		int y = cordinates.getCol();

		if (game.getPlayerOne().getName().equalsIgnoreCase(playerName)) {
			opponent = game.getPlayerTwo();
		} else if (game.getPlayerTwo().getName().equalsIgnoreCase(playerName)) {
			opponent = game.getPlayerOne();
		} else {
			throw new GameException("Invalid player name");
		}

		if (checkIfInvalidCoordinate(x, y)) {
			throw new GameException("Invalid Coordinates");
		}

		if (hasShipsLeft(opponent) && game.getStatus() != GAMEOVER) {
			String shipStatus = shoot(opponent, cordinates);

			if (shipStatus.contains(ShootStatus.MISS.toString())) {
				message = "You missed it !! Try again !!";
			} else if (shipStatus.contains(ShootStatus.HIT.toString())) {
				message = "Good!! You hit it!!";
			} else if (shipStatus.contains(ShootStatus.SUNK.toString())) {
				if (!hasShipsLeft(opponent)) {
					game.setStatus(GAMEOVER);
					message = "CONGRATS!!! " + playerName + ". YOU WON !!!";
				} else {
					message = shipStatus.concat("!! Awesome!!");
				}
			}
		} else {
			throw new GameException("Game over");
		}
		return message;
	}

	/**
	 * Check if coordinates are valid
	 */
	private Boolean checkIfInvalidCoordinate(int x, int y) {
		return (x >= BOARD_SIZE || y >= BOARD_SIZE);
	}

	/**
	 * Checks if there are ships remaining
	 * @throws GameException
	 */
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

	/**
	 * Converting letter to Number
	 */
	private int letterToNumberConversion(char ch) {
		return ch - 'A';
	}

	/**
	 * Converting Number to letter
	 */
	private char numberToLetterConversion(int i) {
		return (char) (i + 'A');
	}

	/**
	 * Checks the value of grid which contains the name of the ship placed
	 * and returns ShipStatus as HIT, MISS or SUNK
	 * If all the grids of a particular ship are hit, then all the grids are marked as SUNK
	 */
	public String shoot(Player opponent, Coordinates cordinates) throws GameException {
		String shipStatus = null;
		int x = letterToNumberConversion(cordinates.getRow());
		int y = cordinates.getCol();
		String shipName = opponent.getBoard().getGrid()[x][y];
		if (ShootStatus.X.toString().equalsIgnoreCase(shipName)) {
			shipStatus = ShootStatus.MISS.toString();
		} else if (ShootStatus.HIT.toString().equalsIgnoreCase(shipName) || ShootStatus.SUNK.toString().equalsIgnoreCase(shipName)) {
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
						ship.getCoordinatesList().forEach(coordinate -> opponent.getBoard().getGrid()[x][y] = ShootStatus.SUNK.toString());
						shipStatus = ship.getName() + " " + ShootStatus.SUNK.toString();
					}
				}
			} else {
				opponent.getBoard().getGrid()[x][y] = ShootStatus.HIT.toString();
				shipStatus = ShootStatus.HIT.toString();
			}

		}
		return shipStatus;
	}

}

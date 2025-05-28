package com.game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.exceptions.GameException;
import com.game.io.Coordinates;
import com.game.io.Ship;
import com.game.service.GameStartService;
import com.game.service.impl.GameStartServiceImpl;


/**
 *  Description : Controller class that handles the placeShip and shoot APIs 
 */
@RequestMapping("/game")
@RestController
public class GameStartController {

	@Autowired
	private GameStartService gameStartService;

	/**
	 *  Invokes the begin method from gameStartService 
	 *  and returns a success message if ships are placed successfully
	 *  else throws an Exception
	 *  
	 * @param playerName - playerName from the URL
	 * @param ships - List of ships from the Request is mapped to this object
	 * @return - ResponseEntity. 
	 */
	@PostMapping("/placeShip/{player}")
	public ResponseEntity<String> startGame(@PathVariable("player") String playerName, @RequestBody List<Ship> ships) {

		try {
			if (gameStartService.begin(ships, playerName))
				return ResponseEntity.status(HttpStatus.OK).body("Successfully placed the ship");
			else
				throw new GameException();
		} catch (GameException e) {
			return ResponseEntity.badRequest().body(e.toString());
		}
	}

	/**
	 *  Invokes the shootBoard method from gameStartService 
	 *  and returns a corresponding message if ships are hit,missed or sunk
	 *  Throws an Exception if the coordinate is an invalid one 
	 *  
	 * @param playerName - playerName from the URL
	 * @param coordinates - Coordinates are passed in the request in the form of row and column
	 * @return ResponseEntity
	 */
	@PostMapping("/shoot/{player}")
	public ResponseEntity<String> shoot(@PathVariable("player") String playerName,
			@RequestBody Coordinates coordinates) {
		String message = null;
		try {
			message = gameStartService.shootBoard(playerName, coordinates);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().body(message);
	}

}

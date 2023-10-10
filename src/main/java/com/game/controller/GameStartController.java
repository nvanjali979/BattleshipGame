package com.game.controller;

import java.util.List;

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

@RequestMapping("/game")
@RestController
public class GameStartController {

	private GameStartService gameStartService = new GameStartService();

	@PostMapping("/placeShip/{player}")
	public ResponseEntity<String> startGame(@PathVariable("player") String playerName, @RequestBody List<Ship> ships) {
		try {
			gameStartService.begin(ships, playerName);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} catch (GameException e) {
			return ResponseEntity.badRequest().body(e.toString());
		}
	}

	@PostMapping("/shoot/{player}")
	public ResponseEntity<String> shoot(@PathVariable("player") String playerName,
			@RequestBody Coordinates coordinates) {
		String message = null;
		try {
			message = gameStartService.shootBoard(playerName, coordinates);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
}

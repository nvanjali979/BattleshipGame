package com.game.service;

import java.util.List;

import com.game.exceptions.GameException;
import com.game.io.Coordinates;
import com.game.io.Ship;

/**
 * Interface class for accessing the methods of GameStartServiceImpl
 */
public interface GameStartService {

	public Boolean begin(List<Ship> ships, String playerName) throws GameException;

	public String shootBoard(String playerName, Coordinates coordinates) throws GameException;

}

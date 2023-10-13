package com.game.exceptions;

/**
 * Description : GameException defined for handling invalid coordinate scenario
 */
public class GameException extends Exception {

	String message;

	public GameException() {
		super();
	}

	public GameException(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}

}

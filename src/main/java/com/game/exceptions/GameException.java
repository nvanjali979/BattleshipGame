package com.game.exceptions;

public class GameException extends Exception{

	String message;
	
	public GameException() {
		super();
	}

	public GameException(String message) {
		super(message);
		this.message =  message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
	
}

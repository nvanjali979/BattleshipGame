package com.game.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class GameExceptionTest {

	@Test
	public void testDefaultConstructor() {
		GameException exception = new GameException();
		assertNull(exception.getMessage());
	}

	@Test
	public void testParameterizedConstructor() {
		String errorMessage = "Invalid coordinates";
		GameException exception = new GameException(errorMessage);
		assertEquals(errorMessage, exception.getMessage());
	}

	@Test
	public void testToString() {
		String errorMessage = "Invalid coordinates";
		GameException exception = new GameException(errorMessage);
		assertEquals(errorMessage, exception.toString());
	}
}

package com.game.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.exceptions.GameException;
import com.game.io.Coordinates;
import com.game.io.Ship;
import com.game.service.GameStartService;

@SpringBootTest
@AutoConfigureMockMvc
public class GameStartControllerTest {

	private GameStartService gameStartService;

	@Autowired
	private MockMvc mockMvc;

	private List<Ship> ships;
	private Coordinates testCoordinates;

	@BeforeEach
	void setUp() {
		ships = new ArrayList<>();
		ships.add(new Ship("Submarine", 3, 'H', 'H', 6));
		ships.add(new Ship("Destroyer", 2, 'V', 'B', 1));
		testCoordinates = new Coordinates('B', 1);
		gameStartService = mock(GameStartService.class);
	}

	@Test
    void testStartGameSuccess() throws Exception {
        when(gameStartService.begin(ships, "playerOne")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/game/placeShip/playerOne")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ships)))
                .andExpect(status().isOk());
    }

	@Test
	void testStartGameFailure() throws Exception {
		ships.add(new Ship("Cruiser", 3, 'V', 'K', 1));
		when(gameStartService.begin(ships, "playerTwo")).thenThrow(new GameException("Invalid coordinate for ship"));

		mockMvc.perform(MockMvcRequestBuilders.post("/game/placeShip/playerTwo").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(ships))).andExpect(status().isBadRequest());
	}

	@Test
    void testShootSuccess() throws Exception {
		when(gameStartService.begin(ships, "playerTwo")).thenReturn(true);
        when(gameStartService.shootBoard("playerOne", testCoordinates)).thenReturn("Hit");

        mockMvc.perform(MockMvcRequestBuilders.post("/game/shoot/playerTwo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testCoordinates)))
                .andExpect(status().isOk());
    }

	@Test
    void testShootFailure() throws Exception {
        when(gameStartService.shootBoard("playerTwo", testCoordinates)).thenThrow(new GameException("MISS"));

        mockMvc.perform(MockMvcRequestBuilders.post("/game/shoot/playerTwo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testCoordinates)))
                .andExpect(status().isBadRequest());
    }

	private String asJsonString(Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}

}

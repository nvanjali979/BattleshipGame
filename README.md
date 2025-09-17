# Battleship Game
Battleship is a classic board game where two players compete against each other to sink each 
other's fleet of ships.

## APIs
1. PlaceShip - Placeship API is used to place ship. String playerOne or playerTwo needs to be passed in the URL to 
   determine which player is placing the ship
2. Shoot - Shoot API is used to shoot the opponents board. Name of player who is shooting needs to be passed in the URL

## How to test in Postman
* http://localhost:8080/game/placeShip/playerOne is used to place the ships of Player 1
* http://localhost:8080/game/placeShip/playerTwo is used to place the ships of Player 2

Sample Request location : https://github.com/nvanjali979/Project1/edit/main/Docs/SamplePlaceShipRequest

* http://localhost:8080/game/shoot/playerOne is called to shoot opponents board by Player 1
* http://localhost:8080/game/shoot/playerOne is called to shoot opponents board by Player 2

Sample Request location : https://github.com/nvanjali979/Project1/edit/main/Docs/SampleShootRequest

Basic Authentication is implemented.
Authorisation header needs to be passed along with the request

# GameServer
This is the backend server for the TicTacToe Android application. The server is built using Java Spring Boot and provides APIs to manage user accounts and game sessions.

## Prerequisites
* Java Development Kit (JDK) 8 or later.
* Database PostgresSQL for storing user data and game sessions.


# Technologies Used
* Java Spring Boot
* Gradle
* PostgresSql

## Endpoints
### User Management
* POST /users - Register a new user.
* GET /users - Log in a user.
* GET /users/score - Get user's score.
### Game Management
* POST /gameItems - Create a new game.
* GET /gameItems - Get joinable games.
* PATCH /gameItems/{gameId} - Join or leave a game.
### Gameplay Management
* POST /games/{gameId}/board - Make a move in the game.
* GET /games/{gameId} - Get game updates.
## Error Handling
The server handles validation and error cases, returning appropriate error responses with meaningful messages.
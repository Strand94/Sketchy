const GameController = require('./controllers/gameController');
const LobbyController = require('./controllers/lobbyController');
const Game = require("./models/game");
const Player = require("./models/player");

var players = [];
for (let i = 0; i<8; i++) {
	players.push(new Player(i.toString(), i.toString()));
}

const game = new Game(players);

const lobbyController = new LobbyController();
lobbyController.createLobby("per", "1337");
lobbyController.joinLobby("ola", "100");
lobbyController.playerDisconnected("1337");

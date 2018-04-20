const LobbyController = require('./controllers/lobbyController');
const Player = require("./models/player");
const Notepad = require("./models/notepad");
const Drawing = require("./models/drawing");


const lobbyController = new LobbyController();
lobbyController.createLobby("per", "persAddress");

const players = [];
for (let i = 0; i < 7; i++) {
    let player = new Player(i.toString(), i.toString());
    player.address = "testAddress";
    players.push(player);
    lobbyController.joinLobby(1, player);
}

lobbyController.startGame(1);
const gameController = lobbyController.lobbies[1].gameController;

for (let i = 0; i < 7; i++) {
    gameController.recieveNotepad(new Notepad(i.toString(), players[i]));
}

var notepad = new Notepad("lemmen", players);
notepad.push(new Drawing("hest", players[0]));
console.log(notepad);

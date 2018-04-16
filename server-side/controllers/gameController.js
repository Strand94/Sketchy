const Game = require("../models/game");

class GameController {
    constructor(lobby, communicator) {
        this.lobby = lobby;
        this.game = null;
        this.communicator = communicator;
        communicator.addGameController(lobby.lobbyId, this);
    }
    
    // "public" functions

    endGame() {
        this.game.abortGame();
    }

    startGame() {
        this.game = new Game(this.lobby.getPlayers);
    }

    // "private" functions

    sendNotepads() {
        
    }

    recieveNotepad() {

    }
}

module.exports = GameController;

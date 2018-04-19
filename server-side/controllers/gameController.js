const Game = require("../models/game");

class GameController {
    constructor(lobby, communicator) {
        this.lobby = lobby;
        this.game = null;
        this.communicator = communicator;
        communicator.addGameController(lobby.lobbyId, this);
    }

    // "public" functions

    abortGame() {
        // TODO: implement
    }

    startGame() {
        this.game = new Game(this.lobby.getPlayers());
        this.continueGame();
    }

    recieveNotepad(notepad) {
        this.game.pushNotepad(notepad);
        if (this.game.getNotepads().length === this.lobby.getPlayers().length) {
            this.continueGame();
        }
    }

    // "private" functions

    continueGame() {
        var gameOver = this.game.nextStep();
        if (gameOver === false) {
            this.sendNotepads();
        } else {
            this.endGame();
        }
    }

    sendNotepads() {
        this.game.getNotepads().forEach(notepad => {
            let player = notepad.nextOnRoute();
            if (typeof player === 'undefined') {
                console.log("no more players on route");
            } else {
                this.communicator.beginRound(player.address, notepad);
            }
        });
    }

    endGame() {
        // TODO: implement
    }

}

module.exports = GameController;

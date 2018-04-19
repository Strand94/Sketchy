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
        this.startRewind();
    }

    startGame() {
        this.game = new Game(this.lobby.getPlayers());
        this.continueGame();
    }

    recieveNotepad(notepad) {
        this.game.addNotepad(notepad);
        if (this.game.getNotepads().length === this.lobby.getPlayers().length) {
            this.continueGame();
        }
    }

    rewindShowNext() {
        this.lobby.getPlayers().forEach(player => {
            this.communicator.rewindShowNext(player.address);
        });
    }

    endRewind() {
        this.lobby.getPlayers().forEach(player => {
            this.communicator.endRewind(player.address);
        });
        this.game = null;
    }

    // "private" functions

    continueGame() {
        const gameOver = this.game.nextStep();
        if (gameOver === false) {
            this.sendNotepads();
        } else {
            this.startRewind();
        }
    }

    sendNotepads() {
        this.game.getNotepads().forEach(notepad => {
            const player = notepad.nextOnRoute();
            if (typeof player === 'undefined') {
                console.log("no more players on route");
            } else {
                this.communicator.beginRound(player.address, notepad);
            }
        });
    }

    startRewind() {
        this.lobby.getPlayers().forEach(player => {
            this.communicator.startRewind(player.address, this.game.getNotepads());
        });
    }

}

module.exports = GameController;

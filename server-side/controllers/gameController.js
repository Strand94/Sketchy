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
        this.game = new Game(this.lobby.players);
        this.continueGame();
    }

    recieveNotepad(notepad) {
        this.game.addNotepad(notepad);
        if (this.game.notepads.length === this.lobby.players.length) {
            this.continueGame();
        }
    }

    rewindShowNext() {
        this.lobby.players.forEach(player => {
            this.communicator.rewindShowNext(player.address);
        });
    }

    endRewind() {
        this.lobby.players.forEach(player => {
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
        this.game.notepads.forEach(notepad => {
            const player = notepad.nextOnRoute();
            if (typeof player === 'undefined') {
                console.log("no more players on route");
            } else {
                this.communicator.beginRound(player, notepad);
            }
        });
    }

    startRewind() {
        this.lobby.players.forEach(player => {
            this.communicator.startRewind(player.address, this.game.notepads);
        });
    }

}

module.exports = GameController;

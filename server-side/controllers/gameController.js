const Game = require("../models/game");

class GameController {
    constructor(lobby, communicator) {
        this.lobby = lobby;
        this.game = null;
        this.communicator = communicator;
        communicator.addGameController(lobby.lobbyId, this);
        this.notepadBuffer = [];
    }

    // "public" functions

    abortGame() {
        this.startRewind();
    }

    startGame() {
        this.game = new Game(this.lobby.players);
        this.continueGame();
    }

    receiveNotepad(notepad) {
        this.notepadBuffer.push(notepad);
        if (this.notepadBuffer.length === this.lobby.players.length) {
            this.game.notepads = this.notepadBuffer;
            this.notepadBuffer = [];
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
        const gameOver = this.game.prepareNextRound();
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
                console.log("No more players on route");
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

const Notepad = require("./notepad");
const guessWords = require("../guessWords");

class Game {
    constructor(players) {
        this.players = players;
        this.notepads = [];

        for (let i = 0; i<this.players.length; i++) {
            var clone = this.players.slice(0);
            this.notepads.push(
                new Notepad(this.getWord(), clone)
            );
            this.rotatePlayers();
        }
    }
    
    abortGame() { }
    nextStep() {

    }
    endGame() {

    }
    getNotepads() {
        return this.notepads;
    }

    // private functions
    getWord() {
        return guessWords.pop();
    }
    rotatePlayers() {
        var tmp = this.players.shift();
        this.players.push(tmp);
    }
}

module.exports = Game;

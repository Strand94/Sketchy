const Sheet = require("./sheet");

class Guess {
    constructor() {
        this.player = null;
        this.guessingObjective = null;
        this.answer = null;
    }
    getPlayer() {
        return this.player;
    }
}
Guess.prototype = Object.create(prototype);

module.exports = Guess;

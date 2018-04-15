const Sheet = require("./sheet");

class Guess extends Sheet{
    constructor() {
        super();
        this.player = null;
        this.guessingObjective = null;
        this.answer = null;
    }
    getPlayer() {
        return this.player;
    }
}

module.exports = Guess;

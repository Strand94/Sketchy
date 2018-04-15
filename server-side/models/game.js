const GameController = require("../controllers/gameController");

class Game {
    constructor() {
        this.counter = 0;
        this.notepads = [];
        this.gameController = new GameController(this);
    }
    abortGame() { }
    nextStep() { }
    endGame() { }
}

module.exports = Game;

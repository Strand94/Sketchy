class GameController {
    constructor() {
        this.game = null;
    }
    endGame() {
        this.game.abortGame();
    }
}

module.exports = GameController;

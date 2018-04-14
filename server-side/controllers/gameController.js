class GameController {
    constructor(game) {
        this.game = game;
    }
    endGame() {
        this.game.abortGame();
    }
}

module.exports = GameController;

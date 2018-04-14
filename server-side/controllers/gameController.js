class GameController(game) {
    constructor() {
        this.game = game;
    }
    endGame() {
        this.game.abortGame();
    }
}

module.exports = GameController;

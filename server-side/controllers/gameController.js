class GameController {
    constructor() {
        this.game = null;
    }
    endGame() {
        this.game.abortGame();
    }
}

export default GameController;

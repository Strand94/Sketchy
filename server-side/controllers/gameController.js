var GameController = function () {
    this.game = null;
};
GameController.prototype.endGame = function () {
    this.game.abortGame();
};

module.exports = GameController;

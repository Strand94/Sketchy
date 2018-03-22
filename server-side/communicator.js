var Communicator = function (gameController, lobbyController) {
    this.gameController = gameController;
    this.lobbyController = lobbyController;
};
Communicator.prototype.updateView = function (playerAddress) {};
Communicator.prototype.ping = function (playerAddress) {};
Communicator.prototype.beginRound = function (playerAddress, sheet) {};
Communicator.prototype.getAnswer = function (playerAddress) {};

module.exports = Communicator;

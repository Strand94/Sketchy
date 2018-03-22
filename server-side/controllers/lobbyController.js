var config = require('../config');

var LobbyController = function () {
    this.lobbies = new Array(config.lobbiesCapacity);
};
LobbyController.prototype.hasLobby = function (lobbyId) {
    return this.lobbies.hasOwnProperty(lobbyId);
};
LobbyController.prototype.startGame = function (lobbyId) {
    if (this.hasLobby(lobbyId)) {
        this.lobbies[lobbyId].startGame();
        return true;
    }
    return false;
};
LobbyController.prototype.joinLobby = function (lobbyId, player) {
    if (this.hasLobby(lobbyId)) {
        this.lobbies[lobbyId].addPlayer(player);
        return true;
    }
    return false;
};
LobbyController.prototype.createLobby = function (lobbyMaster) {
};


module.exports = LobbyController;

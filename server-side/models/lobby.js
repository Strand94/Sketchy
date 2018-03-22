var Game = require('./game');

var Lobby = function (lobbyId, lobbyMaster) {
    this.lobbyId = lobbyId;
    this.players = [];
    this.currentGame = null;
    this.lobbyMaster = lobbyMaster;
};
Lobby.prototype.addPlayer = function (player) {
    this.players.push(player);
};
Lobby.prototype.removePlayer = function (player) {
    var index = this.players.indexOf(player);
    if (index < 0) {
        this.players.splice(index, 1);
        return true;
    }
    return false;
};
Lobby.prototype.startGame = function () {
    this.currentGame = new Game(this.players.length);
};

module.exports = Lobby;

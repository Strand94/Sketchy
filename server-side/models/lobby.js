const Game = require("./game");

class Lobby {
    constructor(lobbyId, lobbyMaster) {
        this.lobbyId = lobbyId;
        this.players = [lobbyMaster];
        this.currentGame = null;
        this.lobbyMaster = lobbyMaster;
    }
    addPlayer(player) {
        this.players.push(player);
    }
    removePlayer(player) {
        var index = this.players.indexOf(player);
        if (index < 0) {
            this.players.splice(index, 1);
            return true;
        }
        return false;
    }
    startGame() {
        this.currentGame = new Game(this.players.length);
    }
    getPlayers() {
        return this.players;
    }
}

module.exports = Lobby;

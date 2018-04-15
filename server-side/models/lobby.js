const GameController = require("../controllers/gameController");

class Lobby {
    constructor(lobbyId, lobbyMaster, communicator) {
        this.lobbyId = lobbyId;
        this.players = [lobbyMaster];
        this.lobbyMaster = lobbyMaster;
        
        this.gameController = new GameController(this, communicator);
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
        this.gameController.startGame();
    }
    getPlayers() {
        return this.players;
    }
}

module.exports = Lobby;

const GameController = require("../controllers/gameController");
const config = require("../config");

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
        const index = this.players.indexOf(player);
        if (index >= 0) {
            this.players.splice(index, 1);
            if (player === this.lobbyMaster) {
                this.lobbyMaster = this.players[0];
            }
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
    isFull() {
        return this.players.length >= config.maxGameSize;
    }
}

module.exports = Lobby;

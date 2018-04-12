const lobbiesCapacity = require("../config");

class LobbyController {
    constructor() {
        this.lobbies = new Array(lobbiesCapacity);
    }
    hasLobby(lobbyId) {
        return this.lobbies.hasOwnProperty(lobbyId);
    }
    startGame(lobbyId) {
        if (this.hasLobby(lobbyId)) {
            this.lobbies[lobbyId].startGame();
            return true;
        }
        return false;
    }
    joinLobby(lobbyId, playerName, playerAdress) {
        // todo: create and add a player
        console.log("join lobby running");

        if (this.hasLobby(lobbyId)) {
            this.lobbies[lobbyId].addPlayer(player);
            return true;
        }
        return false;
    }
    createLobby(lobbyMaster) {
    }
}


module.exports = LobbyController;

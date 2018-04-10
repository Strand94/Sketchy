import { lobbiesCapacity } from "../config";

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
    joinLobby(lobbyId, player) {
        if (this.hasLobby(lobbyId)) {
            this.lobbies[lobbyId].addPlayer(player);
            return true;
        }
        return false;
    }
    createLobby(lobbyMaster) {
    }
}


export default LobbyController;

const lobbiesCapacity = require("../config");
const Player = require("../models/player");

class LobbyController {
    constructor() {
        this.lobbies = new Array(lobbiesCapacity);
        this.playerinLobby = {};    // maps player to lobby
        this.players = {};          // maps playerAdress to Player
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
        if (this.hasLobby(lobbyId)) {
            var player = new Player(playerName, playerAdress)
            this.lobbies[lobbyId].addPlayer(player);
            this.playersLobby[playerAdress] = lobbyId;
            return true;
        }
        return false;
    }
    createLobby(lobbyMaster) {
    }
    playerDisconnected(playerAdress) {
        var player = this.players[playerAdress];
        var lobby = this.lobbies[player];
        
        lobby.removePlayer(player);
        delete this.playerinLobby[player];
        delete this.playerinLobby[player.adress];
    }
}


module.exports = LobbyController;

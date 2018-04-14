const lobbiesCapacity = require("../config");
const Player = require("../models/player");
const Lobby = require("../models/lobby");

class LobbyController {
    constructor() {
        this.lobbies = new Array(lobbiesCapacity);  
        this.playerinLobby = {};    // maps player to lobby
        this.players = {};          // maps playerAdress to Player

        // make unique id's
        var idStack = new Array();
        for (let i=1000; i<10000; i++) {
            idStack.push(i);
        }
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
        if (idStack.length > 0) {
            var lobby = new Lobby(idStack.pop, lobbyMaster);
        } 
    }
    closeLobby(lobbyId) {
        idStack.push(lobbyId);
        var players = this.lobbies[lobbyId].getPlayers();
        for (player in players) {
            delete this.playerinLobby[player];
            delete this.players[player.adress];
        }
    }
    playerDisconnected(playerAdress) {
        var player = this.players[playerAdress];

        this.lobbies[player].removePlayer(player);
        delete this.playerinLobby[player];
        delete this.players[player.adress];
    }
}


module.exports = LobbyController;

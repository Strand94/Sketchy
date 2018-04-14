const lobbiesCapacity = require("../config");
const Player = require("../models/player");
const Lobby = require("../models/lobby");

class LobbyController {
    constructor() {
        this.players = {};          // maps playerAdress to Player
        this.playerinLobby = {};    // maps player to lobbyId
        this.lobbies = new Array(lobbiesCapacity);  // lobbyId -> lobby

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
            this.playerinLobby[player] = lobbyId;
            return true;
        }
        return false;
    }
    createLobby(playerName, playerAdress) {
        if (idStack.length > 0) {
            var player = new Player(playerName, playerAdress);
            var lobbyId = idStack.pop();
            var lobby = new Lobby(lobbyId, player);

            this.lobbies[lobbyId] = lobby;
            this.playerinLobby[player] = lobbyId;
            this.players[playerAdress] = player;
        } 
    }
    closeLobby(lobbyId) {
        var players = this.lobbies[lobbyId].getPlayers();
        for (player in players) {
            delete this.playerinLobby[player];
            delete this.players[player.adress];
        }
        delete this.lobbies[lobbyId];
        idStack.push(lobbyId);
    }
    playerDisconnected(playerAdress) {
        var lobby = [this.lobbies[
            this.playerinLobby[
                this.players[playerAdress]
            ]
        ]];

        if (lobby.getPlayers() < 2) {       // last member of lobby left
            delete this.lobbies[lobby.lobbyId];
        } else {
            lobby.removePlayer(player);     // lobby kept alive
        }

        delete this.playerinLobby[player];
        delete this.players[player.adress];

    }
}


module.exports = LobbyController;

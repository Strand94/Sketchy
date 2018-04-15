const lobbiesCapacity = require("../config");
const Player = require("../models/player");
const Lobby = require("../models/lobby");

class LobbyController {
    constructor() {
        this.players = {};          // maps playerAdress to Player
        this.playerinLobby = {};    // maps player to lobbyId
        this.lobbies = new Array(lobbiesCapacity);  // lobbyId -> lobby

        // make unique id's
        this.idStack = new Array();
        for (let i=9999; i>999; i--) {
            this.idStack.push(i);
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
        if (this.idStack.length > 0) {
            var player = new Player(playerName, playerAdress);
            var lobbyId = this.idStack.pop();
            var lobby = new Lobby(lobbyId, player);

            this.lobbies[lobbyId] = lobby;
            this.playerinLobby[player] = lobbyId;
            this.players[playerAdress] = player;

            console.log("Lobby %d created", lobbyId);
        } 
    }
    closeLobby(lobbyId) {
        var players = this.lobbies[lobbyId].getPlayers();
        for (let player in players) {
            delete this.playerinLobby[player];
            delete this.players[player.adress];
        }
        delete this.lobbies[lobbyId];
        this.idStack.push(lobbyId);
    }
    playerDisconnected(playerAdress) {
        if (this.players.hasOwnProperty(playerAdress)) {  // player has joined a lobby
            var player = this.players[playerAdress];
            var lobby = this.lobbies[this.playerinLobby[player]];
    
            if (lobby.getPlayers().length < 2) {       // last member of lobby left
                delete this.lobbies[lobby.lobbyId];
                console.log("Lobby %d closed", lobby.lobbyId);
            } else {
                lobby.removePlayer(player);     // lobby kept alive
            }
    
            delete this.playerinLobby[player];
            delete this.players[playerAdress];
            
        }

    }
}


module.exports = LobbyController;

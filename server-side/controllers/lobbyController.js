const config = require("../config");
const Player = require("../models/player");
const Lobby = require("../models/lobby");
const Game = require("../models/game");
const Communicator = require("../communicator");

class LobbyController {
    constructor() {
        this.players = {};          // maps playerAdress to Player
        this.playerinLobby = {};    // maps player to lobbyId
        this.lobbies = new Array(config.lobbiesCapacity);  // lobbyId -> lobby
        this.communicator = new Communicator(this);

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
            
            var playerList = this.lobbies[lobbyId].getPlayers();
            playerList.forEach(player => {
                this.communicator.updateLobby(playerAdress, playerList);
            });

            return true;
        }
        return false;
    }
    createLobby(playerName, playerAdress) {
        if (this.idStack.length > 0) {
            var player = new Player(playerName, playerAdress);
            var lobbyId = this.idStack.pop();
            var lobby = new Lobby(lobbyId, player, this.communicator);

            this.lobbies[lobbyId] = lobby;
            this.playerinLobby[player] = lobbyId;
            this.players[playerAdress] = player;

            this.communicator.updateLobby(playerAdress, [player]);

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
        this.communicator.removeGameController(lobbyId);

        console.log("Lobby %d closed", lobbyId);
    }
    playerDisconnected(playerAdress) {
        if (this.players.hasOwnProperty(playerAdress)) {    // player has joined a lobby
            var player = this.players[playerAdress];
            var lobby = this.lobbies[this.playerinLobby[player]];
    
            if (lobby.getPlayers().length < 2) {            // last member of lobby left
                this.closeLobby(lobby.lobbyId);
            } else {
                lobby.removePlayer(player);            
            }
    
            delete this.playerinLobby[player];
            delete this.players[playerAdress];
            
        }

    }
}


module.exports = LobbyController;

const config = require("../config");
const Player = require("../models/player");
const Lobby = require("../models/lobby");
const Communicator = require("../communicator");

class LobbyController {
    constructor() {
        this.allPlayers = {};  // maps playerAddress to Player
        this.lobbies = new Array(config.lobbiesCapacity);  // lobbyId -> lobby
        this.communicator = new Communicator(this);

        // Make unique id's
        this.idStack = [];
        for (let i = this.lobbies.length - 1; i > 0; i--) {
            this.idStack.push(i);
        }
    }

    hasLobby(lobbyId) {
        return this.lobbies[lobbyId] !== undefined;
    }

    startGame(lobbyId) {
        if (this.hasLobby(lobbyId)) {
            this.lobbies[lobbyId].startGame();
            return true;
        }
        return false;
    }

    joinLobby(lobbyId, playerName, playerAddress) {
        if (this.hasLobby(lobbyId)) {
            const player = new Player(playerName, playerAddress, lobbyId);
            this.lobbies[lobbyId].addPlayer(player);
            this.allPlayers[playerAddress] = player;

            const playerList = this.lobbies[lobbyId].getPlayers();
            playerList.forEach(player => {
                this.communicator.updateLobby(playerAddress, playerList);
            });

            console.log("Player %s joined lobby %d", playerName, lobbyId);
            return true;
        }
        return false;
    }

    createLobby(playerName, playerAddress) {
        if (this.idStack.length > 0) {
            const lobbyId = this.idStack.pop();
            const player = new Player(playerName, playerAddress, lobbyId);
            this.lobbies[lobbyId] = new Lobby(lobbyId, player, this.communicator);
            this.allPlayers[playerAddress] = player;

            console.log("Player %s created lobby %d", playerName, lobbyId);
        }
    }

    closeLobby(lobbyId) {
        const players = this.lobbies[lobbyId].getPlayers();

        for (let player in players) {
            delete this.allPlayers[player.address];
        }

        delete this.lobbies[lobbyId];
        this.idStack.push(lobbyId);
        this.communicator.removeGameController(lobbyId);

        console.log("Lobby %d closed", lobbyId);
    }

    playerDisconnected(playerAddress) {
        if (this.allPlayers.hasOwnProperty(playerAddress)) {  // player has joined a lobby
            const player = this.allPlayers[playerAddress];
            const lobby = this.lobbies[player.lobbyId];

            console.log("Player %s disconnected", player.name);
            if (lobby.getPlayers().length <= 1) {  // last member of lobby left
                this.closeLobby(lobby.lobbyId);
            } else {
                lobby.removePlayer(player);
            }

            delete this.allPlayers[playerAddress];

        }

    }
}


module.exports = LobbyController;

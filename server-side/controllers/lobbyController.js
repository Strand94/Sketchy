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
            console.log("Starting game in lobby %d", lobbyId);
            this.lobbies[lobbyId].startGame();
            return true;
        } else {
            console.log("can't start non-existing lobby with id %s", lobbyId);
        }
    }

    joinLobby(lobbyId, playerName, playerAddress) {
        if (this.hasLobby(lobbyId) && !this.lobbies[lobbyId].isFull()) {
            const player = new Player(playerName, playerAddress, lobbyId, false);
            this.lobbies[lobbyId].addPlayer(player);
            this.allPlayers[playerAddress] = player;

            this.updateLobby(lobbyId);
            console.log("Player %s joined lobby %d", playerName, lobbyId);
            return true;
        }
        return false;
    }

    createLobby(playerName, playerAddress) {
        if (this.idStack.length > 0) {
            const lobbyId = this.idStack.pop();
            const player = new Player(playerName, playerAddress, lobbyId, true);
            this.lobbies[lobbyId] = new Lobby(lobbyId, player, this.communicator);
            this.allPlayers[playerAddress] = player;

            this.updateLobby(lobbyId);
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
                this.updateLobby(lobby.lobbyId);
            }

            delete this.allPlayers[playerAddress];

        }
    }

    updateLobby(lobbyId) {
        const players = this.lobbies[lobbyId].getPlayers();

        var playerNames = []
        players.forEach(player => {
            playerNames.push(player.name);
        })

        players.forEach(player => {
            this.communicator.updateLobby(player.address, lobbyId, playerNames);
        });
    }
}


module.exports = LobbyController;

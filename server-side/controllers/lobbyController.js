const config = require("../config");
const Player = require("../models/player");
const Lobby = require("../models/lobby");
const Communicator = require("../communicator");

class LobbyController {
    constructor() {
        this.players = {};          // maps playerAddress to Player
        this.playerinLobby = {};    // maps player to lobbyId
        this.lobbies = new Array(config.lobbiesCapacity);  // lobbyId -> lobby
        this.communicator = new Communicator(this);

        // make unique id's
        this.idStack = new Array();
        for (let i = 9999; i >= 0; i--) {
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
            const player = new Player(playerName, playerAddress);
            this.lobbies[lobbyId].addPlayer(player);
            this.playerinLobby[player] = lobbyId;

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
            const player = new Player(playerName, playerAddress);
            const lobbyId = this.idStack.pop();
            const lobby = new Lobby(lobbyId, player, this.communicator);

            this.lobbies[lobbyId] = lobby;
            this.playerinLobby[player] = lobbyId;
            this.players[playerAddress] = player;

            this.communicator.updateLobby(playerAddress, lobbyId, [player]);

            console.log("Player %s created lobby %d", playerName, lobbyId);
        }
    }

    closeLobby(lobbyId) {
        const players = this.lobbies[lobbyId].getPlayers();

        for (let player in players) {
            delete this.playerinLobby[player];
            delete this.players[player.adress];
        }

        delete this.lobbies[lobbyId];
        this.idStack.push(lobbyId);
        this.communicator.removeGameController(lobbyId);

        console.log("Lobby %d closed", lobbyId);
    }

    playerDisconnected(playerAddress) {
        if (this.players.hasOwnProperty(playerAddress)) {    // player has joined a lobby
            const player = this.players[playerAddress];
            const lobby = this.lobbies[this.playerinLobby[player]];

            console.log("Player %s disconnected", player.name);
            if (lobby.getPlayers().length <= 1) {  // last member of lobby left
                this.closeLobby(lobby.lobbyId);
            } else {
                lobby.removePlayer(player);
            }

            delete this.playerinLobby[player];
            delete this.players[playerAddress];

        }

    }
}


module.exports = LobbyController;

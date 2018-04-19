const app = require('express')();
const server = require('http').createServer(app);
const io = require("socket.io").listen(server);


const events = {
    START_GAME: "start-game",
    END_GAME: "end-game",
    JOIN_LOBBY: "join-lobby",
    CREATE_LOBBY: "create-lobby",
    UPDATE_VIEW: "update-view",
    UPDATE_LOBBY: "update-lobby",
    BEGIN_ROUND: "begin-round",
    GET_ANSWER: "get-answer",
    PING: "ping",
    PING_OK: "pingOK",
    SOCKET_ID: "socketID"
};

class Communicator {
    constructor(lobbyController) {
        this.connections = {};       // maps socket.id to socket
        this.gameControllers = {};   // maps lobbyId to gameController

        server.listen(process.env.PORT || 5000, function () {
            console.log("Server listening on port %d", this.address().port);
        });

        const thiz = this;

        io.on('connection', function (socket) {
            console.log("Connection opened: %s", socket.id);
            socket.emit(events);    // TODO: handle on java side

            socket
                .on('disconnect', () => {
                    lobbyController.playerDisconnected(socket.id);
                    delete thiz.connections[socket.id];
                    console.log("Connection closed: %s", socket.id);
                })
                .on(events.JOIN_LOBBY, (obj) => {
                    thiz.connections[socket.id] = socket;
                    lobbyController.joinLobby(obj.lobbyId, obj.playerName, socket.id);
                })
                .on(events.CREATE_LOBBY, (obj) => {
                    thiz.connections[socket.id] = socket;
                    lobbyController.createLobby(obj.playerName, socket.id);
                })
                .on(events.START_GAME, (obj) => {
                    lobbyController.startGame(obj.lobbyId);
                })
                .on(events.END_GAME, (obj) => {
                    thiz.gameControllers[obj.lobbyId].endGame();
                })
        });
    }

    startGame(playerAddress) {
        if (this.connections[playerAddress] === undefined) {
            console.log("Player with address %s not registered", playerAddress);
        } else {
            this.connections[playerAddress].emit(events.START_GAME);
        }
    }

    endGame(playerAddress) {
        if (this.connections[playerAddress] === undefined) {
            console.log("Player with address %s not registered", playerAddress);
        } else {
            this.connections[playerAddress].emit(events.END_GAME);
        }
    }

    updateView(playerAddress) {
        if (this.connections[playerAddress] === undefined) {
            console.log("Player with address %s not registered", playerAddress);
        } else {
            this.connections[playerAddress].emit(events.UPDATE_VIEW);
        }
    };

    updateLobby(playerAddress, lobbyId, playerList) {
        if (this.connections[playerAddress] === undefined) {
            console.log("Player with address %s not registered", playerAddress);
        } else {
            this.connections[playerAddress].emit(events.UPDATE_LOBBY, lobbyId, playerList);
        }
    }

    ping(playerAddress) {
        if (this.connections[playerAddress] === undefined) {
            console.log("Player with address %s not registered", playerAddress);
        } else {
            this.connections[playerAddress].emit(events.PING);
        }
    };

    beginRound(playerAddress, notepad) {
        if (this.connections[playerAddress] === undefined) {
            console.log("Player with address %s not registered", playerAddress);
        } else {
            this.connections[playerAddress].emit(events.BEGIN_ROUND, notepad);
        }
    };

    addGameController(lobbyId, gameController) {
        this.gameControllers[lobbyId] = gameController;
    }

    removeGameController(lobbyId) {
        if (this.gameControllers[lobbyId]) delete this.gameControllers[lobbyId];
    }
}

module.exports = Communicator;

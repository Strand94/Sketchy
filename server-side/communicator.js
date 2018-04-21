const app = require('express')();
const server = require('http').createServer(app);
const io = require("socket.io").listen(server);

const Notepad = require("./models/notepad");


const events = {
    START_GAME: "start-game",
    END_GAME: "end-game",
    JOIN_LOBBY: "join-lobby",
    CREATE_LOBBY: "create-lobby",
    UPDATE_VIEW: "update-view",
    UPDATE_LOBBY: "update-lobby",
    BEGIN_ROUND: "begin-round",
    GET_ANSWER: "get-answer",
    SEND_ANSWER: "send-answer",
    PING: "ping",
    PING_OK: "pingOK",
    SOCKET_ID: "socketID",
    START_REWIND: "start-rewind",
    REWIND_SHOW_NEXT: "rewind-show-next",
    REWIND_FINISHED: "rewind-finished",
    NOTIFY_PLAYER: "notify-player",
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
                    console.log("Connection closed: %s", socket.id);
                    lobbyController.playerDisconnected(socket.id);
                    delete thiz.connections[socket.id];
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
                .on(events.SEND_ANSWER, (obj) => {
                    let notepad = Object.assign(new Notepad, JSON.parse(obj.notepad));
                    console.log("Receiving: {lobbyId: %d, notepad: %s}", obj.lobbyId, JSON.stringify(notepad));
                    thiz.gameControllers[obj.lobbyId].receiveNotepad(notepad);
                })
                .on(events.END_GAME, (obj) => {
                    thiz.gameControllers[obj.lobbyId].endGame();
                })
                .on(events.REWIND_SHOW_NEXT, (obj) => {
                    thiz.gameControllers[obj.lobbyId].rewindShowNext();
                })
        });
    }

    addGameController(lobbyId, gameController) {
        this.gameControllers[lobbyId] = gameController;
    }

    removeGameController(lobbyId) {
        if (this.gameControllers[lobbyId]) delete this.gameControllers[lobbyId];
    }

    startGame(playerAddress) {
        this.startGame(playerAddress, events.START_GAME);
    }

    endGame(playerAddress) {
        this.endGame(playerAddress, events.END_GAME);
    }

    updateView(playerAddress) {
        this.sendToPlayer(playerAddress, events.UPDATE_VIEW);
    };

    updateLobby(playerAddress, lobbyId, playerList) {
        this.sendToPlayer(playerAddress, events.UPDATE_LOBBY, {"lobbyId": lobbyId, "playerList": playerList});
    }

    ping(playerAddress) {
        this.sendToPlayer(playerAddress, events.PING);
    };

    beginRound(playerAddress, notepad) {
        console.log("Sending: %s", JSON.stringify({"notepad": notepad}));
        this.sendToPlayer(playerAddress, events.BEGIN_ROUND, {"notepad": notepad});
    };

    startRewind(playerAddress, notepadList) {
        this.sendToPlayer(playerAddress, events.START_REWIND, {"notepadList": notepadList});
    }

    rewindShowNext(playerAddress) {
        this.sendToPlayer(playerAddress, events.REWIND_SHOW_NEXT);
    }

    endRewind(playerAddress) {
        this.sendToPlayer(playerAddress, events.REWIND_FINISHED);
    }

    notifyPlayer(playerAddress, message) {
        this.sendToPlayer(playerAddress, events.NOTIFY_PLAYER, {"message": message});
    }

    // "private"

    sendToPlayer(playerAddress, event, args) {
        if (this.connections[playerAddress] === undefined) {
            console.log("Player with address %s not registered", playerAddress);
        } else {
            if (args === undefined) {
                this.connections[playerAddress].emit(event);
            } else {
                this.connections[playerAddress].emit(event, args);
            }
        }
    }
}

module.exports = Communicator;

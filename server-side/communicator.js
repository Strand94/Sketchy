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
}

var connections = {};       // maps socket.id to socket
var gameControllers = {};   // maps lobbyId to gameController

class Communicator {
    constructor(lobbyController) {
        server.listen(process.env.PORT || 5000, function(){
            console.log("Server listening on port %d", this.address().port);
        });

        this.lobbyController = lobbyController;

        io.on('connection', function(socket) {
            console.log("Player Connected!");
            socket.emit(events);    // TODO: handle on java side

            socket
                .on('disconnect', () => {
                    lobbyController.playerDisconnected(socket.id);
                    delete connections[socket.id];
                    console.log("Player disconnected");
                })
                .on(events.JOIN_LOBBY, (obj) => {
                    connections[socket.id] = socket; 
                    lobbyController.joinLobby(obj.lobbyId, obj.playerName, socket.id);
                })
                .on(events.CREATE_LOBBY, (obj) => {
                    connections[socket.id] = socket;
                    lobbyController.createLobby(obj.playerName);
                })
                .on(events.START_GAME, (obj) => {
                    lobbyController.startGame(obj.lobbyId);
                })
                .on(events.END_GAME, (obj) => {
                    gameControllers[obj.lobbyId].endGame;
                })
                .on(events.GET_ANSWER, (obj) => {

                })

        });
    }

    startGame(playerAddress) {
        connections[playerAddress].emit(events.START_GAME);
    }

    endGame(playerAddress) {
        connections[playerAddress].emit(events.END_GAME);
    }
    
    updateView(playerAddress) {
        connections[playerAddress].emit(events.UPDATE_VIEW);
    };

    updateLobby(playerAddress, playerList) {
        console.log("connections: " + connections);
        console.log("connections[playerAddress]: " + connections[playerAddress]);
        connections[playerAddress].emit(events.UPDATE_LOBBY, playerList);
    }

    ping(playerAddress) {
        connections[playerAddress].emit(events.PING);
    };

    beginRound(playerAddress, sheet) {
        connections[playerAddress].emit(events.BEGIN_ROUND, sheet);
    };

    getAnswer(playerAddress) {
        // TODO: figure out logic and return the sheet
        connections[playerAddress].emit(events.getAnswer);
        connections[playerAddress].on(events.GET_ANSWER, (sheet) => {
            
        });
        
    };

    addGameController(lobbyId, gameController) {
        gameControllers[lobbyId] = gameController;
    }

    removeGameController(lobbyId) {
        delete gameControllers[lobbyId];
    }
}

module.exports = Communicator;

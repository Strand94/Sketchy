const app = require('express')();
const server = require('http').createServer(app);
const io = require("socket.io").listen(server);

connections = {};

const events = {
    START_GAME: "start-game",
    END_GAME: "end-game",
    JOIN_LOBBY: "join-lobby",
    CREATE_LOBBY: "create-lobby",
    UPDATE_VIEW: "update-view",
    BEGIN_ROUND: "begin-round",
    GET_ANSWER: "get-answer",
    PING: "ping",
    PING_OK: "pingOK",
    SOCKET_ID: "socketID"
}


class Communicator {
    constructor(gameController, lobbyController) {
        server.listen(8080);
        console.log("Server running..");

        this.gameController = gameController;
        this.lobbyController = lobbyController;

        io.on('connection', function(socket) {
            console.log("Player Connected!");
            socket.emit(events);    // todo: handle on java side

            socket
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
                    gameController.endGame(obj.lobbyId);
                })
                .on(events.GET_ANSWER, (obj) => {

                })

        });
    }
    
    updateView(playerAddress) {
        connections[playerAddress].emit(events.UPDATE_VIEW);
    };

    ping(playerAddress) {
        connections[playerAddress].emit(events.PING);
    };

    beginRound(playerAddress, sheet) {
        connections[playerAddress].emit(events.BEGIN_ROUND, sheet);
    };

    getAnswer(playerAddress) {
        // todo: return the sheet
        connections[playerAddress].emit(events.getAnswer);
        connections[playerAddress].on(events.GET_ANSWER, (sheet) => {
            
        });
        
    };
}

module.exports = Communicator;

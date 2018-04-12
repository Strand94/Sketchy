const app = require('express')();
const server = require('http').createServer(app);
const io = require("socket.io").listen(server);

connections = {};

var events = {
    START_GAME: "start-game",
    END_GAME: "end-game",
    JOIN_LOBBY: "join-lobby",
    CREATE_LOBBY: "create-lobby",
    SOCKET_ID: "socketID",
    PING: "ping",
    PING_OK: "pingOK"
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
                    var lobbyId = obj.lobbyId;
                    var playerName = obj.playerName;
                    var playerAddress = socket.id;

                    connections[playerAddress] = socket; 
        
                    lobbyController.joinLobby(lobbyId, playerName, playerAddress);
                })
                .on(events.CREATE_LOBBY, (obj) => {

                })

        });
    }
    
    updateView(playerAddress) { }

    ping(playerAddress) { }

    beginRound(playerAddress, sheet) { }

    getAnswer(playerAddress) { }
}

module.exports = Communicator;

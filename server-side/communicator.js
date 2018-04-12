const app = require('express')();
const server = require('http').createServer(app);
const io = require("socket.io").listen(server);
users = [];
connections = [];


class Communicator {
    constructor(gameController, lobbyController) {
        server.listen(8080);
        console.log("Server running..")

        this.gameController = gameController;
        this.lobbyController = lobbyController;

        io.on('connection', function(socket) {
            connections.push(socket);
            console.log("Player Connected!");
            socket.emit('socketID', { id: socket.id });
        });
        
        io.on('joinLobby', function(socket) {
            lobbyId = socket.lobbyId;
            playerName = socket.playerName;
            playerAddress = socket.id;

            lobbyController.joinLobby(lobbyId, playerName, playerAddress);
        });
        
        io.on('pingOK', function(socket) {
            console.log("ping");
        });
    }
    
    updateView(playerAddress) { }

    ping(playerAddress) {
        io.emit('ping', { });
        console.log("ping started");
     }
    beginRound(playerAddress, sheet) { }

    getAnswer(playerAddress) { }
}

module.exports = Communicator;

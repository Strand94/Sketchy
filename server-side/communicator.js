const app = require('express')();
const server = require('http').createServer(app);
const io = require("socket.io").listen(server);

connections = {};


class Communicator {
    constructor(gameController, lobbyController) {
        server.listen(8080);
        console.log("Server running..")

        this.gameController = gameController;
        this.lobbyController = lobbyController;

        io.on('connection', function(socket) {
            console.log("Player Connected!");
            
            socket
                .on('join-lobby', function(obj) {
                    var lobbyId = obj.lobbyId;
                    var playerName = obj.playerName;
                    var playerAddress = socket.id;

                    connections[playerAddress] = socket; 
        
                    lobbyController.joinLobby(lobbyId, playerName, playerAddress);
                })

        });
    }
    
    updateView(playerAddress) { }

    ping(playerAddress) { }

    beginRound(playerAddress, sheet) { }

    getAnswer(playerAddress) { }
}

module.exports = Communicator;

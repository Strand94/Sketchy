const app = require('express')();
const server = require('http').Server(app);
const io = require("socket.io")(server);

class Communicator {
    constructor(gameController, lobbyController) {
        this.gameController = gameController;
        this.lobbyController = lobbyController;
    }
    updateView(playerAddress) { }
    ping(playerAddress) { }
    beginRound(playerAddress, sheet) { }
    getAnswer(playerAddress) { }
}

server.listen(8080, function(){
	console.log("Server is now running...");
});

io.on('connection', function(socket) {
    console.log("Player Connected!");
	socket.emit('socketID', { id: socket.id });
});

module.exports = Communicator;

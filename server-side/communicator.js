const app = require('express')();
const server = require('http').Server(app);
const io = require("socket.io")(server);

class Communicator {
    constructor(gameController, lobbyController) {
        this.gameController = gameController;
        this.lobbyController = lobbyController;
    }
    updateView(playerAddress) { }
    ping(playerAddress) {
        io.emit('ping', { });
        console.log("ping started");
     }
    beginRound(playerAddress, sheet) { }
    getAnswer(playerAddress) { }
}

server.listen(8080, function(){
	console.log("Server is now running...");
});

io.addListener('connection', function(socket) {
    console.log("Player Connected!");
	socket.emit('socketID', { id: socket.id });
});

io.on('pingOK', function(socket) {
    console.log("ping");
});

module.exports = Communicator;

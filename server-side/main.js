const gameController = require('./controllers/gameController');
const lobbyController = require('./controllers/lobbyController');
const communicatorClass = require('./communicator');
const communicator = new communicatorClass(gameController, lobbyController);

communicator.ping("tempParam");
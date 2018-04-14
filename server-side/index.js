const GameController = require('./controllers/gameController');
const LobbyController = require('./controllers/lobbyController');
const Communicator = require('./communicator');
const communicator = new Communicator(new GameController(), new LobbyController());


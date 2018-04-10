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

export default Communicator;

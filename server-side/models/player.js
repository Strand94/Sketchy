class Player {
    constructor(name, address, lobbyId, isLobbyMaster) {
        this.name = name;
        this.address = address;
        this.points = 0;
        this.lobbyId = lobbyId;
        this.isLobbyMaster = isLobbyMaster;
    }
}

module.exports = Player;

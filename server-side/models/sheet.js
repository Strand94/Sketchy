class Sheet {
    constructor(objectiveWord, playerName) {
        this.objectiveWord = objectiveWord;
        this.drawing = null;
        this.playerName = playerName;
    }
    
    getPlayerName() {
        return this.playerName;
    }

    setPlayerName(playerName) {
        this.playerName = playerName;
    }

    getDrawing() {
        return this.drawing;
    }

    setDrawing(drawing) {
        this.drawing = drawing;
    }
    
    getObjectiveWord() {
        return this.objectiveWord;
    }

    setObjectiveWord(word) {
        this.objectiveWord = word;
    }
}

module.exports = Sheet;

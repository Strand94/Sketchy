class Sheet {
    constructor() {
        this.objectiveWord = null;
        this.drawing = null;
        this.player = null;
    }

    setPlayer(player) {
        this.player = player;
    }

    setDrawing(drawing) {
        this.drawing = drawing;
    }

    setObjectiveWord(word) {
        this.objectiveWord = word;
    }

    getPlayer() {
        return this.player;
    }

    getDrawing() {
        return this.drawing;
    }

    getObjectiveWord() {
        return this.objectiveWord;
    }
}

module.exports = Sheet;

const Sheet = require("./sheet");

class Drawing extends Sheet {
    constructor(objectiveWord, player) {
        super();
        this.objectiveWord = objectiveWord;
        this.player = player;
    }
}

module.exports = Drawing;

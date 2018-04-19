const Sheet = require("./sheet");

class Guess extends Sheet {
    constructor(objectiveWord, drawing, player) {
        super();
        this.objectiveWord = objectiveWord;
        this.drawing = drawing;
        this.player = player;

        this.answer = null;
    }

    setAnswer(answer) {
        this.answer = answer;
    }

    getAnswer() {
        return this.answer;
    }
}

module.exports = Guess;

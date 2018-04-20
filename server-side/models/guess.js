const Sheet = require("./sheet");

class Guess extends Sheet {
    constructor(objectiveWord, drawing, playerName) {
        super(objectiveWord, playerName);
        this.drawing = drawing;
        
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

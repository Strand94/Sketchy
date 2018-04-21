class Sheet {
    constructor(objectiveWord) {
        this.objectiveWord = objectiveWord;
        this.base64Drawing = null;
        this.answer = null;
        this.drawer = null;
        this.guesser = null;
    }
}

module.exports = Sheet;

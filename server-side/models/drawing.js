const Sheet = require("./sheet");

class Drawing extends Sheet {
    constructor() {
        super();
        this.player = null;
        this.drawingObjective = null;
        this.answer = null;
    }
    getPlayer() {
        return this.player;
    }
}

module.exports = Drawing;

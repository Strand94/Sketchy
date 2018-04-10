const Sheet = require("./sheet");

class Drawing {
    constructor() {
        this.player = null;
        this.drawingObjective = null;
        this.answer = null;
    }
    getPlayer() {
        return this.player;
    }
}

var sheet = Sheet();

Drawing.prototype = Object.create(prototype);

module.exports = Drawing;

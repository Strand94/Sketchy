import Sheet, { prototype } from "./sheet";

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

export default Drawing;

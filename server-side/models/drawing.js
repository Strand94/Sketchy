var Sheet = require('./sheet');

var Drawing = function () {
    this.player = null;
    this.drawingObjective = null;
    this.answer = null;
};
Drawing.prototype = Object.create(Sheet.prototype);
Drawing.prototype.getPlayer = function () {
    return this.player;
};

module.exports = Drawing;

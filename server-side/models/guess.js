var Sheet = require('./sheet');

var Guess = function () {
    this.player = null;
    this.guessingObjective = null;
    this.answer = null;
};
Guess.prototype = Object.create(Sheet.prototype);
Guess.prototype.getPlayer = function () {
    return this.player;
};

module.exports = Guess;

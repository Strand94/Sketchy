var Game = function () {
    this.counter = 0;
    this.notepads = [];
};
Game.prototype.abortGame = function () {};
Game.prototype.nextStep = function () {};
Game.prototype.endGame = function () {};

module.exports = Game;

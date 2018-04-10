import { prototype } from "./sheet";

class Guess {
    constructor() {
        this.player = null;
        this.guessingObjective = null;
        this.answer = null;
    }
    getPlayer() {
        return this.player;
    }
}
Guess.prototype = Object.create(prototype);

export default Guess;

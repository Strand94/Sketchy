const Notepad = require("./notepad");
const guessWords = require("../guessWords");
const Sheet = require("./sheet");   // needed for instanceof.. for some reason

class Game {
    constructor(players) {
        this.players = players;
        this.notepads = [];

        // fills inn notepads with unique routes
        const playerAddresses = [];
        this.players.forEach(player => {
            playerAddresses.push(player.address);
        });
        this.players.forEach(player => {
            const clone = playerAddresses.slice(0);  

            this.notepads.push(
                new Notepad(this.getWord(), clone)
            );

            // rotate player addresses
            let tmp = playerAddresses.shift();
            playerAddresses.push(tmp);
        });

    }

    nextStep() {
        if (this.notepads[0].hasNoSheets()) {   // first round
            this.firstHandleNotepads();
            return false;
        } else if (this.notepads[0].routeIsFinished()) {    // game over
            return true;
        } else {
            this.normalHandleNotepads();
            return false;
        }
    }

    addNotepad(newNotepad) {
        this.notepads.push(newNotepad);
    }

    // private functions

    getWord() {
        return guessWords.pop();
    }

    normalHandleNotepads() {
        // partly fills inn and pushes new sheet to each notepad
        this.notepads.forEach(notepad => {
            const previousSheet = notepad.pop();

            if (previousSheet.answer === null) {
                notepad.push(previousSheet);
            } else {
                notepad.push(new Sheet(
                    previousSheet.answer
                ));
            }
        });
    }

    firstHandleNotepads() {
        this.notepads.forEach(notepad => {
            notepad.push(new Sheet(
                notepad.originalWord
            ));
        });
    }
}

module.exports = Game;

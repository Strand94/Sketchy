const Notepad = require("./notepad");
const guessWords = require("../guessWords");
const Sheet = require("./sheet");   // needed for instanceof.. for some reason

class Game {
    constructor(players) {
        this.players = players;
        this.notepads = [];
        this.guessWords = guessWords.slice();
        for (let i = guessWords.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [guessWords[i], guessWords[j]] = [guessWords[j], guessWords[i]];
        }

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

    prepareNextRound() {
        if (this.notepads[0].hasNoSheets()) {   // first round
            this.createFirstSheet();
            return false;
        } else if (this.notepads[0].routeIsFinished()) {    // game over
            return true;
        } else {
            this.prepareNextSheet();
            return false;
        }
    }

    addNotepad(newNotepad) {
        this.notepads.push(newNotepad);
    }

    // private functions

    getWord() {
        return this.guessWords.pop();
    }

    prepareNextSheet() {
        // partly fills inn and pushes new sheet to each notepad
        this.notepads.forEach(notepad => {
            const previousSheet = notepad.peek();

            if (previousSheet.answer) notepad.push(new Sheet(previousSheet.answer));
        });
    }

    createFirstSheet() {
        this.notepads.forEach(notepad => {
            notepad.push(new Sheet(
                notepad.originalWord
            ));
        });
    }
}

module.exports = Game;

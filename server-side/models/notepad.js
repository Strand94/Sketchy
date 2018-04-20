class Notepad {
    constructor(originalWord, route) {
        this.originalWord = originalWord;
        this.sheets = [];
        this.route = route; // array of playerAdresses
    }

    push(sheet) {
        this.sheets.push(sheet);
    }

    pop() {
        return this.sheets.pop();
    }

    nextOnRoute() {
        return this.route.pop();
    }

    routeIsFinished() {
        return (this.route.length < 1);
    }

    hasNoSheets() {
        return (this.sheets.length < 1);	// no sheets on notepad
    }
}

module.exports = Notepad;

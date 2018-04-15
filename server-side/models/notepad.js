const config = require("../config");

class Notepad {
	constructor(originalWord, route) {
		this.originalWord = originalWord;
		this.sheets = new Array(config.maxGameSize);
		this.route = route;
	}
	pushSheet(sheet) {
		sheets.push(sheet);
	}
	popSheet() {
		return this.sheets.pop();
	}
	nextOnRoute() {
		return this.route.pop();
	}
}

module.exports = Notepad;

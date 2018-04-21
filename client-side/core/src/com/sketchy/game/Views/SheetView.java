package com.sketchy.game.Views;

import com.sketchy.game.Controllers.ClientController;
import com.sketchy.game.Models.Sheet;

public abstract class SheetView extends View {
    protected final ClientController controller;
    private Sheet sheet;

    SheetView(ClientController controller) {
        this.controller = controller;
        sheet = Sheet.LOADING;
    }

    @Override
    public void reset() {
        sheet = Sheet.LOADING;
    }

    protected void onSubmit() {
        controller.submit(sheet, getClass());
    }

    public void setSheet(Sheet sheet) throws Exception {
        if (this.sheet == Sheet.LOADING) this.sheet = sheet;
        else throw new Exception("Sheet already set");
    }

    public Sheet getSheet() {
        return sheet;
    }
}

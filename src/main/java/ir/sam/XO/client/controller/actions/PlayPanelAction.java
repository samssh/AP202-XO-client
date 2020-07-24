package ir.sam.XO.client.controller.actions;

import ir.sam.XO.client.controller.MainController;
import ir.sam.XO.client.controller.request.PutPiece;

public class PlayPanelAction {
    private final MainController mainController;
    public PlayPanelAction(MainController mainController) {
        this.mainController = mainController;
    }

    public void putPiece(int x, int y) {
        mainController.sendRequest(new PutPiece(x,y));
    }
}

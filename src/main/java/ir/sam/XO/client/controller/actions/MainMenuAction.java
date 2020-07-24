package ir.sam.XO.client.controller.actions;

import ir.sam.XO.client.controller.MainController;

public class MainMenuAction {
    private final MainController mainController;

    public MainMenuAction(MainController mainController) {
        this.mainController = mainController;
    }

    public void startGame() {
        mainController.sendStartGame();
    }

    public void replay() {
        mainController.startReplay();
    }

    public void exit(){
        mainController.logout();
    }
}

package ir.sam.XO.client.controller.actions;

import ir.sam.XO.client.controller.MainController;
import ir.sam.XO.client.controller.request.Login;
import ir.sam.XO.client.view.panel.LoginPanel;

public class LoginPanelAction {
    private final MainController mainController;

    public LoginPanelAction(MainController mainController) {
        this.mainController = mainController;
    }

    public void changeMode(LoginPanel loginPanel) {
        if (loginPanel.getMode() == LoginPanel.Mode.SIGN_IN) {
            loginPanel.setMode(LoginPanel.Mode.SIGN_UP);
        } else {
            loginPanel.setMode(LoginPanel.Mode.SIGN_IN);
        }
        loginPanel.reset();
    }

    public void login(LoginPanel loginPanel, String username, String pass, String pass2) {
        if ("Enter username".equals(username) || "".equals(username))
            return;
        if (loginPanel.getMode() == LoginPanel.Mode.SIGN_UP && !pass.equals(pass2)) {
            loginPanel.setMessage("password not same");
            return;
        }
        if ("Enter password".equals(pass))
            return;
        mainController.sendRequest(new Login(username,pass,loginPanel.getMode().getValue()));
    }

}

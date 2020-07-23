package controller.actions;

import controller.MainController;
import view.panel.LoginPanel;

public class LoginPanelAction {
    private final MainController client;

    public LoginPanelAction(MainController client) {
        this.client = client;
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
//        client.sendLoginRequest(username, pass, loginPanel.getMode().getValue());
    }

}

package controller;

import java.util.ArrayList;
import java.util.Map;

public interface ResponseVisitor {
    void login(boolean success, String message);

    void goTo(String message,String panelName);

    void setScoreBoard(ArrayList<Map<String, Object>> players, Map<String, Object> currentPlayer);
}

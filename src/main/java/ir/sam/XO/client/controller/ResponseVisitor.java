package ir.sam.XO.client.controller;

import java.util.ArrayList;
import java.util.Map;

public interface ResponseVisitor {
    void login(boolean success, String message);

    void goTo(String message, String panelName);

    void setScoreBoard(ArrayList<Map<String, Object>> players, Map<String, Object> currentPlayer);

    void setPlayDetails(int eventNumber, int x, int y, String piece, String message
            , String playerPiece, String opponentUsername);
}

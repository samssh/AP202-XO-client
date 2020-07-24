package ir.sam.XO.client.controller.response;

import ir.sam.XO.client.controller.ResponseVisitor;

import java.util.Map;

public class PlayDetailsResponse extends Response {
    private final int eventNumber;
    private final int x, y;
    private final String  piece;
    private final String message;
    private final String  playerPiece;
    private final String opponentUsername;

    public PlayDetailsResponse(Map<String, Object> map) {
        super(map);
        eventNumber = (int) map.get("eventNumber");
        x = (int) map.get("x");
        y = (int) map.get("y");
        piece = (String) map.get("piece");
        message = (String) map.get("message");
        playerPiece = (String) map.get("playerPiece");
        opponentUsername = (String) map.get("opponentUsername");
    }

    @Override
    public void execute(ResponseVisitor responseVisitor) {
        responseVisitor.setPlayDetails(eventNumber,x,y,piece,message,playerPiece,opponentUsername);
    }
}

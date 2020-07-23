package controller.response;

import java.util.ArrayList;
import java.util.Map;

public class PlayDetailsResponse extends Response {
    private final String  piece;
    private final String message;
    private final String opponentUsername;
    private final ArrayList<ArrayList<String>> board;


    @SuppressWarnings("unchecked")
    public PlayDetailsResponse(Map<String, Object> map) {
        super(map);
        piece = (String) map.get("piece");
        message = (String) map.get("message");
        opponentUsername = (String) map.get("opponentUsername");
        board = (ArrayList<ArrayList<String>>) map.get("board");
    }
}

package ir.sam.XO.client.controller.request;

import java.util.Map;

public class PutPiece extends Request {
    private final int x, y;

    public PutPiece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Map<String, Object> toMap() {
        return Map.of("x",x,"y",y);
    }
}

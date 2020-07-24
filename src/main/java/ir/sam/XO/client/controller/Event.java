package ir.sam.XO.client.controller;

import lombok.Getter;

public class Event {
    @Getter
    private final int eventNumber,x,y;
    @Getter
    private final String piece;

    public Event(int eventNumber, int x, int y, String piece) {
        this.eventNumber = eventNumber;
        this.x = x;
        this.y = y;
        this.piece = piece;
    }
}

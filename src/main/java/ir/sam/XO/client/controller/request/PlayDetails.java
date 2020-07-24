package ir.sam.XO.client.controller.request;

import java.util.Map;

public class PlayDetails extends Request{
    private final int eventNumber;
    public PlayDetails(int eventNumber) {
        this.eventNumber = eventNumber;
    }

    @Override
    public Map<String, Object> toMap() {
        return Map.of("eventNumber",eventNumber);
    }
}

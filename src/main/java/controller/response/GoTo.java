package controller.response;

import java.util.Map;

public class GoTo extends Response {
    private final String message;
    private final String panelName;

    public GoTo(Map<String, Object> map) {
        super(map);
        message = (String) map.get("message");
        panelName = (String) map.get("panelName");
    }
}
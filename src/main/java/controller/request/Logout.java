package controller.request;

import java.util.Map;

public class Logout extends Request {
    public Logout() {
        super();
    }

    @Override
    public Map<String, Object> toMap() {
        return Map.of();
    }
}

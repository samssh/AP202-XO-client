package controller.response;

import controller.ResponseVisitor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class LoginResponse extends Response {
    @Getter
    private final boolean success;
    @Getter
    private final String message;
    public LoginResponse(Map<String,Object> map) {
        super(map);
        this.success = (boolean) map.get("success");
        this.message = (String) map.get("message");
    }

    @Override
    public void execute(ResponseVisitor responseVisitor) {
        responseVisitor.login(success,message);
    }
}

package controller.request;

import lombok.Getter;

import java.util.Map;


public class Login extends Request {
    @Getter
    private final String userName, password;
    @Getter
    private final int mode;

    public Login(String userName, String password, int mode) {
        this.userName = userName;
        this.password = password;
        this.mode = mode;
    }

    @Override
    public Map<String, Object> toMap() {
        return Map.of("userName",userName,"password",password,"mode",mode);
    }
}
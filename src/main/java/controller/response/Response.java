package controller.response;

import controller.ResponseVisitor;

import java.util.Map;

public abstract class Response {
    public static final Map<Object, Class<? extends Response>> responseType =
            Map.of("Login", LoginResponse.class
                    , "GoTo", GoTo.class
                    , "PlayDetails", PlayDetailsResponse.class
                    , "ScoreBoard", ScoreBoard.class
                    , "Void", VoidResponse.class);

    public Response(Map<String, Object> map) {
    }

    public abstract void execute(ResponseVisitor responseVisitor);
}

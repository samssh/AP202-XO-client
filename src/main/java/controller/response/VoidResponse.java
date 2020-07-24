package controller.response;

import controller.ResponseVisitor;

import java.util.Map;

public class VoidResponse extends Response {
    public VoidResponse(Map<String, Object> map) {
        super(map);
    }

    @Override
    public void execute(ResponseVisitor responseVisitor) {

    }
}

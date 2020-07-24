package ir.sam.XO.client.controller.response;

import ir.sam.XO.client.controller.ResponseVisitor;

import java.util.Map;

public class VoidResponse extends Response {
    public VoidResponse(Map<String, Object> map) {
        super(map);
    }

    @Override
    public void execute(ResponseVisitor responseVisitor) {

    }
}

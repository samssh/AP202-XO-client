package ir.sam.XO.client.controller.request;

import java.util.Map;

public abstract class Request {
    public abstract Map<String, Object> toMap();
}
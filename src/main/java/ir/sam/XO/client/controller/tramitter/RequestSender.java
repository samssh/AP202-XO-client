package ir.sam.XO.client.controller.tramitter;

import ir.sam.XO.client.controller.request.Request;
import ir.sam.XO.client.controller.response.Response;

public interface RequestSender {
    Response sendRequest(Request request);

}

package controller.tramitter;

import controller.request.Request;
import controller.response.Response;

public interface RequestSender {
    Response sendRequest(Request request);

}

package ir.sam.XO.client.controller.tramitter;

import com.google.gson.*;
import ir.sam.XO.client.controller.request.Request;
import ir.sam.XO.client.controller.response.LoginResponse;
import ir.sam.XO.client.controller.response.Response;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.*;

public class SocketRequestSender implements RequestSender, JsonDeserializer<Map<String, Object>> {
    private final Scanner scanner;
    private final PrintStream printStream;
    private String token;

    public SocketRequestSender(Socket socket) throws IOException {
        printStream = new PrintStream(socket.getOutputStream());
        scanner = new Scanner(socket.getInputStream());
    }

    @Override
    public Response sendRequest(Request request) {
        System.out.println(toJson(request));
        printStream.println(toJson(request));
        printStream.flush();
        String json = scanner.nextLine();
        System.out.println(json);
        return toResponse(json);
    }

    private String toJson(Request request) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", request.getClass().getSimpleName());
        if (token != null)
            map.put("token", token);
        map.put("body", request.toMap());
        return new Gson().toJson(map);
    }

    @SuppressWarnings("unchecked")
    private Response toResponse(String json) {
        Map<String, Object> map = new GsonBuilder().registerTypeAdapter(Map.class, this)
                .create().fromJson(json, Map.class);
        Class<? extends Response> clazz = Response.responseType.get(map.get("type"));
        try {
            Constructor<? extends Response> constructor = clazz.getDeclaredConstructor(Map.class);
            Response response = constructor.newInstance((Map<String, Object>) map.get("body"));
            if (response instanceof LoginResponse) {
                token = (String) map.get("token");
            }
            return response;
        } catch (NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (Map<String, Object>) read(json);
    }

    public Object read(JsonElement in) {
        if (in.isJsonArray()) {
            List<Object> list = new ArrayList<>();
            JsonArray arr = in.getAsJsonArray();
            for (JsonElement anArr : arr) {
                list.add(read(anArr));
            }
            return list;
        } else if (in.isJsonObject()) {
            Map<String, Object> map = new HashMap<>();
            JsonObject obj = in.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entitySet = obj.entrySet();
            for (Map.Entry<String, JsonElement> entry : entitySet) {
                map.put(entry.getKey(), read(entry.getValue()));
            }
            return map;
        } else if (in.isJsonPrimitive()) {
            JsonPrimitive prim = in.getAsJsonPrimitive();
            if (prim.isBoolean()) {
                return prim.getAsBoolean();
            } else if (prim.isString()) {
                return prim.getAsString();
            } else if (prim.isNumber()) {
                Number num = prim.getAsNumber();
                return num.intValue();
            }
        }
        return null;
    }
}

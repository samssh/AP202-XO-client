package ir.sam.XO.client;

import ir.sam.XO.client.controller.MainController;
import ir.sam.XO.client.controller.tramitter.SocketRequestSender;
import ir.sam.XO.client.util.Config;
import ir.sam.XO.client.view.ImageLoader;

import java.io.IOException;
import java.net.Socket;

public class Main {
    static int port;
    static String host;
    public static void main(String[] args) throws IOException {
        Config config = new Config("src/main/resources/client.properties");
        port = config.getProperty(Integer.class,"port").orElse(8000);
        host = config.getProperty(String.class,"host").orElse("");
        Socket socket = new Socket(host,port);
        ImageLoader.setConfig(config);
        new MainController(new SocketRequestSender(socket),config);
    }
}

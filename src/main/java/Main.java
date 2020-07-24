import controller.MainController;
import controller.tramitter.SocketRequestSender;
import util.Config;

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
        new MainController(new SocketRequestSender(socket),config);
    }
}

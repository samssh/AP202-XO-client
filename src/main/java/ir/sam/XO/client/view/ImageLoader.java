package ir.sam.XO.client.view;

import ir.sam.XO.client.util.Config;

import javax.swing.*;

public class ImageLoader {
    private static Config config;
    private static ImageLoader instance;
    static {
        config = new Config();
    }

    public static void setConfig(Config config) {
        ImageLoader.config = config;
        getInstance();
    }

    public static ImageLoader getInstance() {
        if (instance==null){
            instance = new ImageLoader();
        }
        return instance;
    }

    private final ImageIcon x,o,empty;

    private ImageLoader(){
        x = new ImageIcon(config.getProperty(String.class,"X")
                .orElse("src/main/resources/X.png"));
        o = new ImageIcon(config.getProperty(String.class,"O")
                .orElse("src/main/resources/O.png"));
        empty = new ImageIcon(config.getProperty(String.class,"EMPTY")
                .orElse("src/main/resources/EMPTY.png"));
    }

    public ImageIcon getEmpty() {
        return empty;
    }

    public ImageIcon getX() {
        return x;
    }

    public ImageIcon getO() {
        return o;
    }
}


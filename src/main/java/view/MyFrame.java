package view;

import util.Config;
import util.Loop;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private int fps;
    private int width = 800, height = 600;

    public MyFrame(Config config) {
        this.config(config);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        new Loop(fps, this::update).start();
    }

    private void config(Config frameConfig) {
        frameConfig.getProperty(Integer.class, "width").ifPresent(integer -> width = integer);
        frameConfig.getProperty(Integer.class, "height").ifPresent(integer -> height = integer);
        setSize(new Dimension(width,height));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(false);
        fps = frameConfig.getProperty(Integer.class, "frame-fps").orElse(60);
    }

    @Override
    public void setContentPane(Container contentPane) {
        super.setContentPane(contentPane);
        super.revalidate();
        super.repaint();
    }

    private void update() {
        super.revalidate();
        super.repaint();
    }
}

package ir.sam.XO.client.view.panel;

import ir.sam.XO.client.util.Config;
import ir.sam.XO.client.view.ButtonListener;
import ir.sam.XO.client.view.ImageLoader;

import javax.swing.*;

public abstract class AbstractPanel extends JPanel {
    protected final BoardPanel boardPanel;
    protected final int width, height;
    protected final int buttonHeight, boardWidth, boardHeight, buttonWidth;

    public AbstractPanel(Config config, ButtonListener listener) {
        setLayout(null);
        boardWidth = config.getProperty(Integer.class, "boardWidth").orElse(7);
        boardHeight = config.getProperty(Integer.class, "boardHeight").orElse(7);
        buttonWidth = config.getProperty(Integer.class, "buttonWidth").orElse(50);
        buttonHeight = config.getProperty(Integer.class, "buttonHeight").orElse(50);
        width = config.getProperty(Integer.class, "width").orElse(350);
        height = config.getProperty(Integer.class, "height").orElse(550);
        setBounds(0, 0, width, height);
        boardPanel = new BoardPanel(listener, boardWidth, boardHeight, buttonWidth, buttonHeight);
        boardPanel.setBounds(0, 0, boardWidth * buttonWidth, boardHeight * buttonHeight);
        this.add(boardPanel);
    }

    public void setAt(int x, int y, String pieceName) {
        boardPanel.setAt(x, y, toIcon(pieceName));
    }

    protected ImageIcon toIcon(String pieceName) {
        if (pieceName == null) return null;
        switch (pieceName) {
            case "X":
                return ImageLoader.getInstance().getX();
            case "O":
                return ImageLoader.getInstance().getO();
            case "EMPTY":
                return ImageLoader.getInstance().getEmpty();
        }
        return null;
    }

    public void reset(){
        boardPanel.reset();
    }
}

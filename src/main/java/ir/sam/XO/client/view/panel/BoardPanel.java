package ir.sam.XO.client.view.panel;

import ir.sam.XO.client.view.ButtonListener;
import ir.sam.XO.client.view.ImageLoader;

import javax.swing.*;

public class BoardPanel extends JPanel {
    private final ButtonListener buttonListener;
    private final BoardButton[][] buttons;

    public BoardPanel(ButtonListener buttonListener, int width, int height
            , int buttonWidth, int buttonHeight) {
        this.buttonListener = buttonListener;
        buttons = new BoardButton[width][height];
        setLayout(null);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buttons[i][j] = new BoardButton(i, j);
                buttons[i][j].setBounds(i * buttonWidth, j * buttonHeight, buttonWidth, buttonHeight);
                this.add(buttons[i][j]);
            }
        }
        reset();
    }

    public void reset(){
        for (BoardButton[] button : buttons) {
            for (BoardButton boardButton : button) {
                boardButton.setIcon(ImageLoader.getInstance().getEmpty());
            }
        }
    }

    public void setAt(int x, int y, ImageIcon imageIcon) {
        buttons[x][y].setIcon(imageIcon);
    }

    private class BoardButton extends JButton {
        BoardButton(final int x, final int y) {
            addActionListener(e -> buttonListener.action(x, y));
        }
    }
}

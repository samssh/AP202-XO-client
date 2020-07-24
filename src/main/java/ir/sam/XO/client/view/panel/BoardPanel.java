package ir.sam.XO.client.view.panel;

import ir.sam.XO.client.view.ButtonListener;

import javax.swing.*;

public class BoardPanel extends JPanel {
    private final ButtonListener buttonListener;

    public BoardPanel(ButtonListener buttonListener,int width,int height) {
        this.buttonListener = buttonListener;
    }


    class BoardButton extends JButton{
        private final int x,y;
        BoardButton(int x, int y) {
            this.x = x;
            this.y = y;
            addActionListener(e -> buttonListener.action(x,y));
        }
    }
}

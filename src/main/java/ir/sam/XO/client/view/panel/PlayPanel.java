package ir.sam.XO.client.view.panel;

import ir.sam.XO.client.controller.actions.PlayPanelAction;
import ir.sam.XO.client.util.Config;
import ir.sam.XO.client.view.ImageLoader;

import javax.swing.*;

public class PlayPanel extends AbstractPanel {
    private final JLabel message, opponentName, piece;

    public PlayPanel(Config config, PlayPanelAction playPanelAction) {
        super(config, playPanelAction::putPiece);
        message = new JLabel();
        opponentName = new JLabel();
        piece = new JLabel();
        message.setBounds(0, boardHeight * buttonHeight, 200 * width / 350, 50);
        piece.setBounds(200 * width / 350, boardHeight * buttonHeight + 30, buttonWidth, buttonHeight);
        opponentName.setBounds(0, boardHeight * buttonHeight + 50, 200 * width / 350, 50);
        this.add(message);
        this.add(opponentName);
        this.add(piece);
    }

    public void setMessages(String message, String opponentName, String playerPiece) {
        if (!message.equals("")) this.message.setText(message);
        if (!opponentName.equals("")) this.opponentName.setText(opponentName);
        if (!playerPiece.equals("")) this.piece.setIcon(toIcon(playerPiece));
    }

    @Override
    public void reset() {
        super.reset();
        message.setText("");
        opponentName.setText("");
        piece.setIcon(ImageLoader.getInstance().getEmpty());
    }
}

package view.panel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class DownPanel extends JPanel {
    private final JLabel name;
    private final JButton playGame,replay, exit;
    private final Dimension dimension;

    public DownPanel(Dimension dimension) {
        setOpaque(false);
        this.dimension = dimension;
        name = new JLabel();
        playGame = new JButton("play");
        replay = new JButton("replay");
        exit = new JButton("exit");
        init();
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        add(name);
        add(playGame);
        add(replay);
        add(exit);
    }

    private void initButtons() {
        playGame.setPreferredSize(dimension);
        playGame.setSize(dimension);
        replay.setPreferredSize(dimension);
        replay.setSize(dimension);
        exit.setPreferredSize(dimension);
        exit.setSize(dimension);
    }


    private void init() {
        initButtons();
        initLabel();
    }

    private void initLabel() {
        name.setSize(dimension);
        name.setPreferredSize(dimension);
    }

    public void setDataForName(Map<String, Object> currentPlayer) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        for (String key : currentPlayer.keySet()) {
            builder.append(key);
            builder.append(":");
            builder.append(currentPlayer.get(key));
            builder.append("<br>");
        }
        builder.append("</html>");
        name.setText(builder.toString());
    }
}

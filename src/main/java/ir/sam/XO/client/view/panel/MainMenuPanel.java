package ir.sam.XO.client.view.panel;

import ir.sam.XO.client.controller.actions.MainMenuAction;
import ir.sam.XO.client.util.Config;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

public class MainMenuPanel extends JPanel {
    private final JScrollPane scrollPane;
    private final MyModel model;
    private final DownPanel downPanel;
    private int componentWidth, componentHeight;
    private int tableWidth, tableHeight;

    public MainMenuPanel(Config config, MainMenuAction mainMenuAction) {
        config(config);
        Object[] columnNames = {"username", "score", "state"};
        model = new MyModel(columnNames, columnNames.length);
        JTable table = new JTable(model);
        scrollPane = new JScrollPane(table);
        initTable();
        this.add(scrollPane);
        Dimension dimension = new Dimension(componentWidth, componentHeight);
        downPanel = new DownPanel(dimension,mainMenuAction);
        downPanel.setBounds(0,tableHeight,tableWidth,getHeight()-tableHeight);
        this.add(downPanel);
    }

    private void initTable() {
        scrollPane.setLocation(0, 0);
        scrollPane.setSize(tableWidth, tableHeight);
        scrollPane.setPreferredSize(new Dimension(tableWidth, tableHeight));
    }

    public void setData(ArrayList<Map<String, Object>> players, Map<String, Object> currentPlayer) {
        downPanel.setDataForName(currentPlayer);
        setDataForTable(players);
    }

    private void setDataForTable(ArrayList<Map<String, Object>> players) {
        Vector<Vector<?>> vectors = new Vector<>();
        for (Map<String, Object> map : players) {
            vectors.add(new Vector<>(Arrays.asList(map.get("username"), map.get("score"), map.get("state"))));
        }
        model.removeAllAndAddAll(vectors);
        System.out.println(model.getRowCount());
    }

    private void config(Config config) {
        setBounds(0, 0,
                config.getProperty(Integer.class, "width").orElse(350),
                config.getProperty(Integer.class, "height").orElse(550));
        tableWidth = config.getProperty(Integer.class, "tableWidth").orElse(350);
        tableHeight = config.getProperty(Integer.class, "tableHeight").orElse(200);
        setPreferredSize(new Dimension(config.getProperty(Integer.class, "width")
                .orElse(350), config.getProperty(Integer.class, "height")
                .orElse(550)));
        componentWidth = config.getProperty(Integer.class, "componentWidth")
                .orElse(200);
        componentHeight = config.getProperty(Integer.class, "componentHeight")
                .orElse(50);
    }

    static class MyModel extends DefaultTableModel {
        public MyModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @SuppressWarnings("SynchronizeOnNonFinalField")
        public void removeAllAndAddAll(Vector<Vector<?>> vectors) {
            synchronized (dataVector){
                dataVector.clear();
                dataVector.addAll(vectors);
            }
            fireTableDataChanged();
        }
    }
}

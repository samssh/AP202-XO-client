package ir.sam.XO.client.view.panel;

import ir.sam.XO.client.controller.Event;
import ir.sam.XO.client.controller.MainController;
import ir.sam.XO.client.util.Config;
import ir.sam.XO.client.view.ButtonListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ReplayPanel extends AbstractPanel {
    private static final ButtonListener dummyListener = (x, y) -> {
    };
    private final MainController mainController;
    private final List<Event> events;
    private int index;
    private final JButton next;

    public ReplayPanel(Config config, MainController mainController) {
        super(config, dummyListener);
        this.mainController = mainController;
        events = new ArrayList<>();
        index = 1;
        next = new JButton("next");
        int w = width, h = height;
        next.setBounds(150 * w / 350, 400 * h / 550, 50 * w / 350, 30 * h / 550);
        next.addActionListener(e -> this.next());
    }

    public void setEvents(List<Event> events) {
        super.reset();
        this.events.clear();
        this.events.addAll(events);
        index = 1;
    }

    private void next() {
        if (index < events.size()) {
            setAt(events.get(index).getX(), events.get(index).getY(), events.get(index).getPiece());
            index++;
        }else {
            mainController.endReplay();
        }
    }

}

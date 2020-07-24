package ir.sam.XO.client.controller;

import ir.sam.XO.client.controller.actions.LoginPanelAction;
import ir.sam.XO.client.controller.actions.MainMenuAction;
import ir.sam.XO.client.controller.request.PlayDetails;
import ir.sam.XO.client.controller.request.Request;
import ir.sam.XO.client.controller.request.ScoreBoard;
import ir.sam.XO.client.controller.response.Response;
import ir.sam.XO.client.controller.tramitter.RequestSender;
import lombok.Getter;
import lombok.Setter;
import ir.sam.XO.client.util.Config;
import ir.sam.XO.client.util.Loop;
import ir.sam.XO.client.view.MyFrame;
import ir.sam.XO.client.view.PanelType;
import ir.sam.XO.client.view.panel.LoginPanel;
import ir.sam.XO.client.view.panel.MainMenuPanel;

import javax.swing.*;
import java.util.*;

import static ir.sam.XO.client.view.PanelType.LOGIN;
import static ir.sam.XO.client.view.PanelType.MAIN_MENU;

public class MainController implements ResponseVisitor {
    private final JFrame frame;
    @Getter
    private final Map<PanelType, JPanel> panels;
    @Getter
    private final Stack<PanelType> history;
    @Getter
    @Setter
    private PanelType now;
    private final List<Request> tempRequestList, requestList;
    private final Loop executor, updater;
    @Getter
    private final RequestSender requestSender;

    public MainController(RequestSender requestSender, Config config) {
        SwingUtilities.invokeLater(() -> {
        });
        this.requestSender = requestSender;
        this.frame = new MyFrame(config);
        panels = new EnumMap<>(PanelType.class);
        history = new Stack<>();
        panels.put(LOGIN, new LoginPanel(new LoginPanelAction(this), config));
        now = LOGIN;
        updateFrame();
        panels.put(MAIN_MENU, new MainMenuPanel(config, new MainMenuAction(this)));
//        panels.put(PLAY, new PlayPanel(new PlayAction(connector, this)));
        tempRequestList = new LinkedList<>();
        requestList = new LinkedList<>();
        executor = new Loop(60, this::executeResponse);
        executor.start();
        updater = new Loop(1, this::updatePanel);
        updater.start();
    }

    private void updateFrame() {
        frame.setContentPane(panels.get(now));
    }

    private void executeResponse() {
        synchronized (tempRequestList) {
            requestList.addAll(tempRequestList);
            tempRequestList.clear();
        }
        for (Request request : requestList) {
            Response response = requestSender.sendRequest(request);
            response.execute(this);
        }
        requestList.clear();
    }

    public void sendRequest(Request request) {
        if (request != null) {
            synchronized (tempRequestList) {
                tempRequestList.add(request);
            }
        }
    }

    private void updatePanel() {
        switch (now) {
            case PLAY:
                sendRequest(new PlayDetails());
                break;
            case MAIN_MENU:
                sendRequest(new ScoreBoard());
        }
    }

    @Override
    public void login(boolean success, String message) {
        LoginPanel panel = (LoginPanel) panels.get(LOGIN);
        if (success) {
            panel.reset();
            now = MAIN_MENU;
            updateFrame();
        } else {
            panel.setMessage(message);
        }
    }

    @Override
    public void goTo(String message, String panelName) {
        PanelType p;
        try {
            p = PanelType.valueOf(panelName);
            if (message != null)
                JOptionPane.showMessageDialog(frame, message, "goto", JOptionPane.INFORMATION_MESSAGE);
            now = p;
            updateFrame();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Override
    public void setScoreBoard(ArrayList<Map<String, Object>> players, Map<String, Object> currentPlayer) {
        ((MainMenuPanel) panels.get(MAIN_MENU)).setData(players, currentPlayer);
    }
}

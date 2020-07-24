package controller;

import controller.actions.LoginPanelAction;
import controller.actions.MainMenuAction;
import controller.request.PlayDetails;
import controller.request.Request;
import controller.request.ScoreBoard;
import controller.response.Response;
import controller.tramitter.RequestSender;
import lombok.Getter;
import lombok.Setter;
import util.Config;
import util.Loop;
import view.MyFrame;
import view.PanelType;
import view.panel.LoginPanel;
import view.panel.MainMenuPanel;

import javax.swing.*;
import java.util.*;

import static view.PanelType.LOGIN;
import static view.PanelType.MAIN_MENU;

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
        try {
            PanelType p = PanelType.valueOf(panelName);
            boolean flag = message == null || JOptionPane.showConfirmDialog(frame, message, "goto",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
            if (flag) {
                now = p;
                updateFrame();
            }
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Override
    public void setScoreBoard(ArrayList<Map<String, Object>> players, Map<String, Object> currentPlayer) {
        ((MainMenuPanel) panels.get(MAIN_MENU)).setData(players, currentPlayer);
    }
}

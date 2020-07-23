package controller;

import controller.actions.LoginPanelAction;
import controller.request.Request;
import controller.response.Response;
import controller.tramitter.RequestSender;
import lombok.Getter;
import lombok.Setter;
import util.Config;
import util.Loop;
import view.MyFrame;
import view.PanelType;
import view.panel.LoginPanel;

import javax.swing.*;
import java.util.*;

import static view.PanelType.LOGIN;

public class MainController implements ResponseVisitor{
    private final JFrame frame;
    @Getter
    private final Map<PanelType, JPanel> panels;
    @Getter
    private final Stack<PanelType> history;
    @Getter
    @Setter
    private PanelType now;
    private final List<Request> tempResponseList, responseList;
    private final Loop executor;
    @Getter
    private final RequestSender requestSender;

    public MainController(RequestSender requestSender, Config config) {
        SwingUtilities.invokeLater(() -> {
        });
        this.requestSender = requestSender;
        this.frame = new MyFrame(config);
        panels = new EnumMap<>(PanelType.class);
        history = new Stack<>();
        panels.put(LOGIN, new LoginPanel(new LoginPanelAction(this),config));
        now = LOGIN;
        updateFrame();
//        panels.put(MAIN_MENU, new MainMenuPanel(new MainMenuAction(connector, this)));
//        panels.put(PLAY, new PlayPanel(new PlayAction(connector, this)));
        tempResponseList = new LinkedList<>();
        responseList = new LinkedList<>();
        executor = new Loop(60, this::executeResponse);
        executor.start();
    }

    private void updateFrame() {
        frame.setContentPane(panels.get(now));
    }

    private void executeResponse() {
        synchronized (tempResponseList) {
            responseList.addAll(tempResponseList);
            tempResponseList.clear();
        }
//        responseList.forEach(response -> response.execute(this));
        responseList.clear();
    }

    public void addResponse(Request request) {
        if (request != null) {
            synchronized (tempResponseList) {
                tempResponseList.add(request);
            }
        }
    }

}

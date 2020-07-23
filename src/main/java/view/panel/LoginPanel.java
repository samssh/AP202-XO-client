package view.panel;

import controller.actions.LoginPanelAction;
import lombok.Getter;
import lombok.Setter;
import util.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class LoginPanel extends JPanel {
    @Setter
    @Getter
    private Mode mode;
    private JLabel welcome;
    private JTextField userName;
    private JPasswordField password, passwordAgain;
    private JButton login, changeMode;
    private int componentWidth, componentHeight, componentSpace;
    private final Dimension dimension;
    private final LoginPanelAction loginPanelAction;

    public LoginPanel(LoginPanelAction loginPanelAction, Config config) {
        setLayout(null);
        config(config);
        dimension = new Dimension(componentWidth, componentHeight);
        initialize();
        this.loginPanelAction = loginPanelAction;
        mode = Mode.SIGN_IN;
        this.add(welcome);
        this.add(userName);
        this.add(password);
        this.add(passwordAgain);
        this.add(login);
        this.add(changeMode);
    }

    private void initialize() {
        initializeChangeMode();
        initializeWelcome();
        initializeLoginB();
        initializeUsername();
        initializePasswords();
    }

    private void initializeWelcome() {
        welcome = new JLabel("welcome", SwingConstants.CENTER);
        welcome.setSize(dimension);
        welcome.setForeground(Color.RED);
//        Constant.makeWhite(welcome);
    }

    private void initializeLoginB() {
        login = new JButton();
        login.setSize(dimension);
        login.addActionListener(actionListener -> loginPanelAction.login(
                this,userName.getText(),String.valueOf(password.getPassword())
                ,String.valueOf(passwordAgain.getPassword())));
//        Constant.makeTransparent(login);
    }



    private void initializeChangeMode() {
        changeMode = new JButton();
        changeMode.setSize(dimension);
        changeMode.addActionListener(actionEvent -> loginPanelAction.changeMode(this));
//        Constant.makeTransparent(changeMode);
    }

    private void initializeUsername() {
        userName = new JTextField("Enter username");
        userName.setSize(dimension);
        userName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if ("Enter username".equals(userName.getText())) {
                    userName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if ("".equals(userName.getText())) {
                    userName.setText("Enter username");
                }
            }
        });
//        Constant.makeWhite(userName);
    }

    private void initializePasswords() {
        password = new JPasswordField();
        password.setSize(dimension);
        initializePass(password);
        passwordAgain = new JPasswordField();
        passwordAgain.setVisible(false);
        passwordAgain.setSize(dimension);
        initializePass(passwordAgain);
    }

    private void initializePass(JPasswordField password1) {
        char passwordChar = password1.getEchoChar();
        password1.setEchoChar((char) 0);
        password1.setText("Enter password");
        password1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if ("Enter password".equals(String.valueOf(password1.getPassword()))) {
                    password1.setText("");
                    password1.setEchoChar(passwordChar);

                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if ("".equals(String.valueOf(password1.getPassword()))) {
                    password1.setText("Enter password");
                    password1.setEchoChar((char) 0);
                }
            }
        });
//        Constant.makeWhite(password1);
    }

    private void resetComponents() {
        welcome.setText("welcome");
        userName.setText("Enter username");
        welcome.requestFocus();
        password.setText("Enter password");
        password.setEchoChar((char) 0);
        passwordAgain.setText("Enter password");
        passwordAgain.setEchoChar((char) 0);
    }

    public void setMessage(String message) {
        welcome.setText(message);
    }

    public void reset() {
        if (mode == Mode.SIGN_IN)
            passwordAgain.setVisible(false);
        if (mode == Mode.SIGN_UP)
            passwordAgain.setVisible(true);
        resetComponents();
    }

    public enum Mode {
        SIGN_IN(1), SIGN_UP(2);
        @Getter
        int value;
        Mode(int value){
            this.value = value;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        super.paintComponent(graphics2D);
        int startWidth = (this.getWidth() - componentWidth) / 2;
        int startHeight = this.getHeight() / 2;
        int sumHeight = componentHeight + componentSpace;
        if (mode == Mode.SIGN_IN) {
            startHeight = startHeight - (4 * sumHeight + componentHeight) / 2;
            login.setLocation(startWidth, startHeight + 3 * sumHeight);
            changeMode.setLocation(startWidth, startHeight + 4 * sumHeight);
            login.setText("sign in");
            changeMode.setText("sign up");
        }
        if (mode == Mode.SIGN_UP) {
            startHeight = startHeight - (5 * sumHeight + componentHeight) / 2;
            passwordAgain.setLocation(startWidth, startHeight + 3 * sumHeight);
            login.setLocation(startWidth, startHeight + 4 * sumHeight);
            changeMode.setLocation(startWidth, startHeight + 5 * sumHeight);
            login.setText("sign up");
            changeMode.setText("sign in");
        }
        welcome.setLocation(startWidth, startHeight);
        userName.setLocation(startWidth, startHeight + sumHeight);
        password.setLocation(startWidth, startHeight + 2 * sumHeight);
    }

    private void config(Config config) {
        setBounds(0,0,
                config.getProperty(Integer.class, "width").orElse(800),
                config.getProperty(Integer.class, "height").orElse(600));
        componentWidth = config.getProperty(Integer.class, "componentWidth").orElse(200);
        componentHeight = config.getProperty(Integer.class, "componentHeight").orElse(50);
        componentSpace = config.getProperty(Integer.class, "componentSpace").orElse(20);
    }
}
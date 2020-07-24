package ir.sam.XO.client.view.panel;

import ir.sam.XO.client.controller.actions.LoginPanelAction;
import lombok.Getter;
import lombok.Setter;
import ir.sam.XO.client.util.Config;

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
    private int componentWidth, componentHeight;
    private final Dimension dimension;
    private final LoginPanelAction loginPanelAction;

    public LoginPanel(Config config, LoginPanelAction loginPanelAction) {
        super();
        config(config);
        dimension = new Dimension(componentWidth, componentHeight);
        initialize();
        this.loginPanelAction = loginPanelAction;
        mode = Mode.SIGN_IN;
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(grid);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(welcome, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(userName, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(password, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(passwordAgain, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(login, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        this.add(changeMode, gbc);
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
        welcome.setPreferredSize(dimension);
        welcome.setSize(dimension);
        welcome.setForeground(Color.RED);
    }

    private void initializeLoginB() {
        login = new JButton();
        login.setPreferredSize(dimension);
        login.setSize(dimension);
        login.addActionListener(actionListener -> loginPanelAction.login(
                this, userName.getText(), String.valueOf(password.getPassword())
                , String.valueOf(passwordAgain.getPassword())));
    }


    private void initializeChangeMode() {
        changeMode = new JButton();
        changeMode.setPreferredSize(dimension);
        changeMode.setSize(dimension);
        changeMode.addActionListener(actionEvent -> loginPanelAction.changeMode(this));
    }

    private void initializeUsername() {
        userName = new JTextField("Enter username");
        userName.setPreferredSize(dimension);
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
    }

    private void initializePasswords() {
        password = new JPasswordField();
        password.setPreferredSize(dimension);
        password.setSize(dimension);
        initializePass(password);
        passwordAgain = new JPasswordField();
        passwordAgain.setVisible(false);
        passwordAgain.setPreferredSize(dimension);
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

        Mode(int value) {
            this.value = value;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        super.paintComponent(graphics2D);
        if (mode == Mode.SIGN_IN) {
            login.setText("sign in");
            changeMode.setText("sign up");
        }
        if (mode == Mode.SIGN_UP) {
            login.setText("sign up");
            changeMode.setText("sign in");
        }
    }

    private void config(Config config) {
        setBounds(0, 0,
                config.getProperty(Integer.class, "width").orElse(800),
                config.getProperty(Integer.class, "height").orElse(600));
        setPreferredSize(new Dimension(config.getProperty(Integer.class, "width")
                .orElse(800), config.getProperty(Integer.class, "height")
                .orElse(600)));
        componentWidth = config.getProperty(Integer.class, "componentWidth").orElse(200);
        componentHeight = config.getProperty(Integer.class, "componentHeight").orElse(50);
    }
}
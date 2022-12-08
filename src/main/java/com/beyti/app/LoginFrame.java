package com.beyti.app;

import javax.swing.*;

public class LoginFrame extends JFrame {

    private JButton loginButton;
    private JPanel mainPanel;

    public LoginFrame() {
        setContentPane(mainPanel);
        setTitle("Login");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

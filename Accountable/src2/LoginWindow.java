package com.accountable.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginWindow extends JFrame {

    private JTextField txtUsername;
    private JTextField txtPassword;

    public LoginWindow() {
        setTitle("Accountable - Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialize();
    }

    private void initialize() {
        // Layout and components initialization code goes here
    }

    // Additional methods and event handling code
}

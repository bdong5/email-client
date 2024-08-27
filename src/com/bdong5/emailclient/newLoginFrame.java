package com.bdong5.emailclient;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Properties;

public class newLoginFrame extends JFrame implements ActionListener {

    JLabel labelStatus;
    JButton btnLogin;
    Boolean loggedIn;
    Session session;
    ImageIcon imageLogo;
    JLabel labelLogo;
    JTextField emailTextField;
    JPasswordField passwordField;

    public newLoginFrame() throws UnsupportedLookAndFeelException {

        UIManager.setLookAndFeel(new FlatIntelliJLaf());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 550); // Adjust frame size as needed
        this.setLocationRelativeTo(null); // Center the frame

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, -3);

        JPanel loginPanel = new JPanel(new GridBagLayout());

        GridBagConstraints loginGbc = new GridBagConstraints();
        loginPanel.setBorder(null);
        loginGbc.insets = new Insets(5, 10, 5, 10);

        // Resize the logo image
        ImageIcon originalIcon = new ImageIcon("LogoWithName.png"); // Replace with your logo path
        Image img = originalIcon.getImage();
        Image resizedImage = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imageLogo = new ImageIcon(resizedImage);
        labelLogo = new JLabel(imageLogo);
        labelLogo.setHorizontalAlignment(JLabel.CENTER);

        JLabel emailLabel = new JLabel("Email");
        emailTextField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(20);

        btnLogin = new JButton("Sign in");
        btnLogin.setFont(btnLogin.getFont().deriveFont(Font.BOLD)); // Set font to bold
        btnLogin.setFocusable(false);
        btnLogin.setBackground(Color.decode("#0097B2"));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.addActionListener(this);

        loginGbc.gridx = 0; // column
        loginGbc.gridy = 0; // row
        loginGbc.gridwidth = 1; // Adjust as needed
        loginGbc.gridheight = 1; // Adjust as needed
        loginGbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(labelLogo, loginGbc);

        loginGbc.gridx = 0; // column
        loginGbc.gridy = 1; // row
        loginGbc.gridwidth = 1; // Reset to default
        loginGbc.anchor = GridBagConstraints.WEST; // Align to the left
        loginPanel.add(emailLabel, loginGbc);

        loginGbc.gridx = 0; // column
        loginGbc.gridy = 2; // row
        loginGbc.gridwidth = 1; // Reset to default
        loginGbc.anchor = GridBagConstraints.WEST; // Align to the left
        loginPanel.add(emailTextField, loginGbc);

        loginGbc.gridx = 0;
        loginGbc.gridy = 3;
        loginGbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(passwordLabel, loginGbc);

        loginGbc.gridx = 0;
        loginGbc.gridy = 4;
        loginGbc.gridwidth = 1; // Reset to default
        loginGbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(passwordField, loginGbc);

        loginGbc.gridx = 0;
        loginGbc.gridy = 5;
        loginGbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(btnLogin, loginGbc);

        labelStatus = new JLabel("Welcome to GlobalMail.");
        labelStatus.setOpaque(false);
        loginGbc.gridx = 0;
        loginGbc.gridy = 6;
        loginGbc.weighty = 2;
        loginGbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(labelStatus, loginGbc);

        // Create image panel and resize the image
        JPanel imagePanel = new JPanel();
        ImageIcon imageIcon = resizeImageIcon("mountain.jpg", 500, 500); // Resize image as needed
        JLabel imageLabel = new JLabel(imageIcon);
        imagePanel.setBorder(null);
        imagePanel.add(imageLabel);

        // Add login panel to main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5; // Take up half of the horizontal space
        gbc.weighty = 1.0; // Take up all vertical space
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        mainPanel.add(loginPanel, gbc);

        // Add image panel to main panel
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5; // Take up half of the horizontal space
        gbc.weighty = 1.0; // Take up all vertical space
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        mainPanel.add(imagePanel, gbc);

        this.add(mainPanel);
        this.setVisible(true);
    }

    // Method to resize an ImageIcon
    private static ImageIcon resizeImageIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }


    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", "imap.gmail.com");
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.ssl.enable", "true");
        properties.put("mail.imaps.ssl.protocols", "TLSv1.2");
        return properties;
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        new newLoginFrame();
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            Arrays.fill(passwordChars, ' '); // Clear password in memory
            if (EmailReceiver.login(emailTextField.getText(), password)) {
                loggedIn = true;
                dispose();
                Arrays.fill(passwordChars, ' '); // Clear password in memory again
            } else {
                labelStatus.setText("Wrong email or password. \nPlease try again.");
                Arrays.fill(passwordChars, ' '); // Clear password in memory again
                System.out.println("Login failed");
            }
        }
    }
}

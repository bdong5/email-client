package com.bdong5.emailclient;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Properties;

public class LoginFrame extends JFrame implements ActionListener {
    JLabel labelEmail, labelPassword, labelStatus, labelLogo;
    JTextField txtEmail;
    JPasswordField passwordField;
    JButton btnLogin;
    ImageIcon imageLogo;
    Boolean loggedIn = false;
    Session session;

    public LoginFrame() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());

            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(500, 350);
            this.setUndecorated(true);

            JPanel titleBar = new JPanel(new BorderLayout());
            titleBar.setBackground(Color.decode("#0097B2"));
            titleBar.setBorder(new EmptyBorder(5, 5, 5, 5)); // Padding

            ImageIcon logoIcon = new ImageIcon("LogoIcon.png"); // Replace with your logo path
            ImageIcon originalIcon = new ImageIcon("LogoWithName.png"); // Replace with your logo path

            Image img = originalIcon.getImage();
            Image resizedImage = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            imageLogo = new ImageIcon(resizedImage);

            NoScalingIcon icon = new NoScalingIcon(imageLogo);

            labelLogo = new JLabel(icon);
            labelLogo.setHorizontalAlignment(JLabel.CENTER);

            labelStatus = new JLabel("Wrong email or password. \nPlease try again.");
            labelStatus.setVisible(false);

            labelEmail = new JLabel("Email");

            labelPassword = new JLabel("Password");

            btnLogin = new JButton("Sign In");

            btnLogin.setFont(btnLogin.getFont().deriveFont(Font.BOLD)); // Set font to bold
            btnLogin.setFocusable(false);
            btnLogin.setBackground(Color.decode("#0097B2"));
            btnLogin.setForeground(Color.WHITE);
            btnLogin.addActionListener(this);

            txtEmail = new JTextField();
            Dimension textFieldSize = new Dimension(250, 30); // width, height
            txtEmail.setPreferredSize(textFieldSize);
            txtEmail.setMaximumSize(textFieldSize);
            txtEmail.setMinimumSize(textFieldSize);
            txtEmail.setCaretColor(Color.GRAY);
            txtEmail.setMargin(new Insets(0, 5, 0, 0)); // Insets: top, left, bottom, right

            passwordField = new JPasswordField();
            passwordField.setPreferredSize(textFieldSize);
            passwordField.setMaximumSize(textFieldSize);
            passwordField.setMinimumSize(textFieldSize);
            passwordField.setMargin(new Insets(0, 5, 0, 0));

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // top, left, bottom, right

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10); // Add padding

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // Span two columns
            gbc.weighty = 1; // Increase weight to push components downwards
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(labelLogo, gbc); // Add logo at the top center

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1; // Reset to default
            gbc.weighty = 0; // Reset weight for other components
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(labelEmail, gbc); // Add email label at (0, 1)

            gbc.gridx = 1; // Change column to 1
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(txtEmail, gbc); // Add email text field at (1, 1)

            gbc.gridx = 0; // Change column to 0
            gbc.gridy = 2; // Change row to 2
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(labelPassword, gbc); // Add password label at (0, 2)

            gbc.gridx = 1; // Change column to 1
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(passwordField, gbc); // Add password field at (1, 2)

            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridwidth = 2;
            panel.add(btnLogin, gbc); // Align login button with the logo

            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(labelStatus, gbc);

            // Centering the JFrame on the screen
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - getWidth()) / 2;
            int y = (screenSize.height - getHeight()) / 2;
            setLocation(x, y);

            this.setIconImage(logoIcon.getImage());
            this.add(panel);
            this.setResizable(false);
            this.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            Arrays.fill(passwordChars, ' '); // Clear password in memory

            if (login(txtEmail.getText(), password)) {
                loggedIn = true;
                dispose();
                Arrays.fill(passwordChars, ' '); // Clear password in memory again
//                new MainFrame(); // Replace with your MainFrame class
            } else {
                labelStatus.setVisible(true);
                Arrays.fill(passwordChars, ' '); // Clear password in memory again
                System.out.println("Login failed");
            }
        }
    }

    private boolean login(String username, String password) {
        Properties properties = getProperties(); // Replace with your email properties setup

        session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Check if the session is connected successfully
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
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

    public static void main(String[] args) {
        new LoginFrame();
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public Session getSession() {
        return session;
    }
}

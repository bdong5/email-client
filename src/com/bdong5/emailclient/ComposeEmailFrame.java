package com.bdong5.emailclient;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class ComposeEmailFrame extends JFrame {

    private Store store;
    private Session session;
    private JTextField toTextField;
    private JTextField subjectTextField;
    private JTextArea emailBody;

    public ComposeEmailFrame(Store store) throws UnsupportedLookAndFeelException {
        this.store = store;

        // Initialize the session
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true"); // enable smtp authentication
        properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS secure comm.
        properties.put("mail.smtp.host", "smtp.gmail.com"); // setup gmail host server
        properties.put("mail.smtp.port", "587"); // set port number as string
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // setup ssl trust to gmail
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Adjust version as necessary

        // Assuming the store object contains the username and password
        String username = store.getURLName().getUsername();
        String password = store.getURLName().getPassword();

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        UIManager.setLookAndFeel(new FlatIntelliJLaf());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setTitle("New Email");

        this.setLayout(new BorderLayout(10, 10)); // Main frame layout

        // Top panel
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel toLabel = new JLabel("To");
        toTextField = new JTextField();
        addField(topPanel, toLabel, toTextField, gbc, 0);

        // Subject field
        JLabel subjectLabel = new JLabel("Subject");
        subjectTextField = new JTextField();
        addField(topPanel, subjectLabel, subjectTextField, gbc, 1);

        // Email body
        emailBody = new JTextArea();
        emailBody.setLineWrap(true);
        emailBody.setWrapStyleWord(true);
        JScrollPane bodyScrollPane = new JScrollPane(emailBody);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnSend = new JButton("Send");
        btnSend.setBackground(Color.decode("#0097B2"));
        btnSend.setForeground(Color.WHITE);
        btnSend.setFocusable(false);
        buttonPanel.add(btnSend);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setForeground(Color.DARK_GRAY);
        btnCancel.setFocusable(false);
        buttonPanel.add(btnCancel);

        // Adding panels to the frame
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bodyScrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Center the frame on the screen
        this.setLocationRelativeTo(null);

        // Set the frame visible after adding all components
        this.setVisible(true);

        // Add action listeners
        btnSend.addActionListener(e -> {
            try {
                sendEmail(store, session, toTextField.getText(), subjectTextField.getText(), emailBody.getText());
            } catch (MessagingException messagingException) {
                messagingException.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to send email", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> this.dispose());
    }

    private void addField(JPanel panel, JLabel label, JTextField field, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0.0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    public static void sendEmail(Store store, Session session, String to, String subject, String text) throws MessagingException {
        String username = store.getURLName().getUsername();
        String password = store.getURLName().getPassword();

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(session.getProperty("mail.smtp.user")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            System.out.println("Email failed to send");
            e.printStackTrace();
            throw e; // Re-throw the exception to indicate failure
        }
    }
}
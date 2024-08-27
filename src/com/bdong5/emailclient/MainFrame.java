package com.bdong5.emailclient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class MainFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private ImageIcon imageLogo;

    public MainFrame(ArrayList<Email> emails) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500, 500);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Left panel (Sidebar)
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Color.decode("#E8F0FE"));

        JButton composeButton = new JButton("Compose");
        composeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(composeButton);
        sidebar.add(Box.createVerticalStrut(20)); // Spacer

        String[] labels = {"Inbox", "Starred", "Snoozed", "Important", "Sent", "Drafts", "Clients", "Personal", "Project Clover", "Project Dot", "Project Hedgehog", "Project Rocket", "Project Skyline"};
        for (String label : labels) {
            JLabel labelItem = new JLabel(label);
            labelItem.setAlignmentX(Component.LEFT_ALIGNMENT);
            sidebar.add(labelItem);
            sidebar.add(Box.createVerticalStrut(5)); // Spacer
        }

        mainPanel.add(sidebar, BorderLayout.WEST);

        // Logo panel
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setPreferredSize(new Dimension(200, 150)); // Adjust size of the logo panel as needed
        logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Logo with text
        ImageIcon logoImage = new ImageIcon("LogoWithName.png"); // Replace with your actual logo image

        Image img = logoImage.getImage();
        Image resizedImage = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        imageLogo = new ImageIcon(resizedImage);

        // Create NoScalingIcon with the resized image
        NoScalingIcon icon = new NoScalingIcon(imageLogo);

        JLabel logoLabel = new JLabel(icon, JLabel.CENTER);
        logoLabel.setHorizontalTextPosition(JLabel.RIGHT); // Adjust text position relative to the icon
        logoLabel.setVerticalTextPosition(JLabel.CENTER); // Adjust text position relative to the icon
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // Adjust font and size as needed
        logoPanel.add(logoLabel, BorderLayout.CENTER);

        mainPanel.add(logoPanel, BorderLayout.NORTH);

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setPreferredSize(new Dimension(this.getWidth(), 50)); // Set preferred height for search panel

        // Search field and button
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30)); // Adjust width and height of the search field
        searchPanel.add(searchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(80, 30)); // Adjust width and height of the search button
        searchPanel.add(searchButton, BorderLayout.EAST);

        mainPanel.add(searchPanel, BorderLayout.CENTER);

        // Center panel (Emails table)
        String[] columnNames = {"Sender", "Subject", "Date"};
        Object[][] data = convertEmailsToArray(emails); // Convert emails ArrayList to table data

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        mainPanel.add(scrollPane, BorderLayout.SOUTH); // Change to SOUTH for the table

        this.add(mainPanel);
        this.setVisible(true);
    }

    // Helper method to convert ArrayList of Email objects to Object array for table display
    private Object[][] convertEmailsToArray(ArrayList<Email> emails) {
        Object[][] data = new Object[emails.size()][3]; // Changed to 3 columns for Sender, Subject, Date
        for (int i = 0; i < emails.size(); i++) {
            Email email = emails.get(i);
            data[i][0] = email.getFrom(); // sender
            data[i][1] = email.getSubject(); // subject
            data[i][2] = ""; // Placeholder for Date, modify as needed
        }
        return data;
    }

//    public static void main(String[] args) {
//        // Simulate receiving emails (replace with actual method call to EmailReceiver.receiveEmails)
//        ArrayList<Email> emails = new ArrayList<>();
//        emails.add(new Email("sender1@example.com", "Subject 1", "Content of email 1", new Date()));
//        emails.add(new Email("sender2@example.com", "Subject 2", "Content of email 2", new Date()));
//
//        SwingUtilities.invokeLater(() -> new MainFrame(emails));
//    }
}

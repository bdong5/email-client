package com.bdong5.emailclient;

import javax.mail.Session;
import javax.mail.Store;
import javax.swing.*;

public class MainWindow {
    public MainWindow(Store store) throws UnsupportedLookAndFeelException {
        new ComposeEmailFrame(store);
    }
}

package com.webapp.controller;

import com.webapp.repository.ConnectionSaver;

public interface MainController {
    default void exitApp() {
        ConnectionSaver.closeConnection();
        System.exit(0);
    }
}

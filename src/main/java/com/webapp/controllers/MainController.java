package com.webapp.controllers;

import com.webapp.repositories.ConnectionSaver;

public interface MainController {
    default void exitApp() {
        ConnectionSaver.closeConnection();
        System.exit(0);
    }
}

package com.project.controllers;

import com.project.repositories.ConnectionSaver;

public interface MainController {
    default void exitApp() {
        ConnectionSaver.closeConnection();
        System.exit(0);
    }
}

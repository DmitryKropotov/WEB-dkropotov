package com.webapp.controller;

public interface MainController {
    default void exitApp() {
        System.exit(0);
    }
}

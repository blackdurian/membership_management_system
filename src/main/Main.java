package main;

import main.java.controllers.PathConfig;
import main.java.views.UiControl;
import main.java.views.users.LoginFrame;

import java.io.IOException;
import java.util.logging.*;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static Handler fileHandler = null;

    public static void main(String[] args) {
        initLogger();
        LOGGER.info("System Run");
        fileHandler.close();
        UiControl.mainFrame = new LoginFrame();
        UiControl.initMainFrame(UiControl.mainFrame);
    }

    private static void initLogger() {
        try {
            fileHandler = new FileHandler(PathConfig.LOGGER_PATH, true); // Creating FileHandler
            fileHandler.setLevel(Level.ALL); // Setting Level to ALL
            fileHandler.setFormatter(new SimpleFormatter()); // Setting formatter to the handler
            LOGGER.addHandler(fileHandler); // Assigning handler to logger
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", e);
        }
    }
}

package main.java.views;


import main.java.controllers.PathConfig;
import main.java.models.users.UserLogin;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class UiControl {

    public final static String WINDOW_TITLE = " - Membership management System";
    public static JFrame mainFrame;
    public static JFrame secondFrame;
    public static JFrame thirdFrame;

    public static void initMainFrame(JFrame frame) {
        try {
            ImageIcon logo = PathConfig.LOGO;
            frame.setIconImage(logo.getImage());
            frame.setBounds(70, 70, 720, 540);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void initSubFrame(JFrame frame) {

        try {
            ImageIcon logo = PathConfig.LOGO;
            frame.setIconImage(logo.getImage());
            //TODO: set close this frame
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    if (mainFrame == null) {
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    } else {
                        mainFrame.setVisible(true);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    }
                }
            });
            frame.setBounds(70, 70, 540, 540);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package main.java.views.users;

import main.java.controllers.LoginControl;
import main.java.controllers.Season;
import main.java.views.UiControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordFrame extends JFrame {
    private JButton btnSave;
    private JButton btnCancel;
    private JPanel rootPanel;
    private JPasswordField pswField;
    private JPasswordField confirmPswField;

    public ChangePasswordFrame(String userId) throws HeadlessException {
        super("Change Password" + UiControl.WINDOW_TITLE);
        add(rootPanel);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!emptyValidation()) {
                        throw new Exception("Field is empty!");
                    }
                    String password = new String(pswField.getPassword());
                    String confirmPassword = new String(confirmPswField.getPassword());
                    if (!password.equals(confirmPassword)) {
                        throw new Exception("Confirm password need same with password.");
                    }
                    LoginControl loginControl = new LoginControl();
                    loginControl.editPasswordById(userId, password);
                    Season.setUser(null); // clear user
                    UiControl.mainFrame = new LoginFrame();
                    UiControl.initMainFrame(UiControl.mainFrame);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    System.out.println(ex.getMessage());
                    //TODO:logger
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UiControl.mainFrame.setVisible(true);
                dispose();
            }
        });
    }

    private boolean emptyValidation() {
        boolean isValid = false;
        if (!(pswField.getPassword().length == 0
                || confirmPswField.getPassword().length == 0)) {
            isValid = true;
        }
        return isValid;
    }

}

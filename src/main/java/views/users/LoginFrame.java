package main.java.views.users;

import main.java.controllers.LoginControl;
import main.java.controllers.PathConfig;
import main.java.controllers.Season;
import main.java.views.UiControl;
import main.java.models.users.UserLogin;
import main.java.views.dashboard.DashboardFrame;
import main.java.views.employee.EmployeeCreateFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.*;

public class LoginFrame extends JFrame {

    private JPanel rootPanel;
    private JButton btnLogin;
    private JButton btnCreateId;
    private JLabel lblSignIn;
    private JLabel lblUsername;
    private JPasswordField txtPassword;
    private JTextField txtUsername;
    private JLabel lblPassword;
    private static final Logger LOGGER = Logger.getLogger(LoginFrame.class.getName());
    private static Handler fileHandler = null;

    public LoginFrame() {
        super("LoginFrame" + UiControl.WINDOW_TITLE);
        initLogger();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int reply = JOptionPane.showConfirmDialog(
                        null
                        , "Do you really want to exit?"
                        , "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    if (fileHandler==null){
                        initLogger();
                    }
                    LOGGER.info("System Exit");
                    LoginFrame.fileHandler.close();
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
        add(rootPanel);
        btnLogin.addActionListener(e -> {
            //input validation
            if (txtUsername.getText() == null || txtUsername.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty.");
            } else if (txtPassword.getPassword() == null || txtPassword.getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "Password cannot be empty.");
            } else {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                try {
                    LoginControl loginControl = new LoginControl();
                    if (loginControl.isPasswordVerify(username, password)) { // password validation
                        UserLogin user = loginControl.getUserByUsername(username);
                        Season.setUser(user);
                        UiControl.mainFrame = new DashboardFrame();
                        UiControl.initMainFrame(UiControl.mainFrame);
                        String logMessage = String.format("Login: %s, Role: %s", username, user.getType());
                        if (fileHandler==null){
                            initLogger();
                        }
                        LOGGER.info(logMessage);
                        fileHandler.close();
                        dispose();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });
        btnCreateId.addActionListener(e -> {
            fileHandler.close();
            setVisible(false);
            UiControl.secondFrame = new EmployeeCreateFrame();
            UiControl.initSubFrame(UiControl.secondFrame);
        });
    }

    private static void initLogger() {
        try {
            fileHandler = new FileHandler(PathConfig.LOGGER_PATH, true); // Creating FileHandler
            fileHandler.setLevel(Level.ALL); // Setting Level to ALL
            fileHandler.setFormatter(new SimpleFormatter()); // Setting formatter to the handler
            LOGGER.addHandler(fileHandler);  // Assigning handler to logger
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", e);
        }
    }
/*private boolean emptyValidation() throws Exception {
    AtomicBoolean isValid = new AtomicBoolean(false);
    if (txtUsername.getText() == null||txtUsername.getText().trim().isEmpty()){
        isValid.set(false);
        throw new Exception("Username cannot be empty.");
    }

    if (txtPassword.getPassword()==null||txtPassword.getPassword().length == 0){
        isValid.set(false);
        throw new Exception("Password cannot be empty.");
    }
    return isValid.get();
}*/
}

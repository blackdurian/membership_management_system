package main.java.views.users;


import main.java.controllers.EmployeeControl;
import main.java.controllers.LoginControl;
import main.java.controllers.Season;
import main.java.views.UiControl;
import main.java.models.employees.Employee;
import main.java.models.employees.Manager;
import main.java.models.employees.Staff;
import main.java.models.users.UserLogin;
import main.java.views.dashboard.DashboardFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserCreateFrame extends JFrame {
    private JButton btnBack;
    private JButton btnCreate;
    private JPasswordField txtConfirmPassword;
    private JPasswordField txtPassword;
    private JTextField txtUsername;
    private JLabel lblHeading;
    private JPanel rootPanel;


    private Employee employee;


    public UserCreateFrame() {
        super("Create Login Account" + UiControl.WINDOW_TITLE);
        add(rootPanel);
        //TODO LOGGER

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UiControl.secondFrame.setVisible(true);
                dispose();
            }
        });

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!emptyValidation()){
                        throw new Exception("Field is empty!");
                    }
                    String username = txtUsername.getText().trim();
                    String password = new String(txtPassword.getPassword());
                    String confirmPassword  = new String(txtConfirmPassword.getPassword());
                    if (!password.equals(confirmPassword)){ // Valid Confirm Password
                        throw new Exception("Confirm password need same with password.");
                    }
                    LoginControl loginControl = new LoginControl();
                    EmployeeControl employeeControl = new EmployeeControl();
                    if (employee instanceof Manager) {
                        employeeControl.addNewManager((Manager)employee);
                    } else if (employee instanceof Staff) {
                        employeeControl.addNewStaff((Staff)employee);
                    } else {
                        throw new NoSuchFieldException("Object has no reference");
                    }
                    loginControl.addNewUser(employee.getID()
                                            ,username
                                            ,password
                                            ,employee.getClass().getSimpleName());
                    UiControl.mainFrame.dispose();              // dispose Login frame
                    UiControl.secondFrame.dispose();            // dispose Employee create frame
                    UserLogin user = loginControl.getUserByUsername(username);
                    Season.setUser(user);
                    UiControl.mainFrame = new DashboardFrame(); // Add Dashboard to main frame
                    UiControl.initMainFrame(UiControl.mainFrame);
                    dispose(); // dispose this frame
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                    ex.printStackTrace();
                    System.out.println(ex.getMessage());
                }
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UiControl.secondFrame.setVisible(true);
                dispose();
            }
        });
    }

    private boolean emptyValidation() {
        boolean isValid = false;
        if (!(txtUsername.getText().trim().isEmpty()
                || txtPassword.getPassword().length == 0
                || txtConfirmPassword.getPassword().length == 0)){
            isValid =true;
        }
            return isValid;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}

package main.java.views.employee;

import main.java.controllers.EmployeeControl;
import main.java.views.UiControl;
import main.java.models.employees.Employee;
import main.java.views.dashboard.DashboardFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class EditProfileFrame extends JFrame {
    private JPanel rootPanel;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtIcNum;
    private JTextField txtPhone;
    private JButton btnCancel;
    private JButton btnSave;
    private Employee employee;

    public EditProfileFrame(Employee employee) throws HeadlessException {
        super("Edit Profile" + UiControl.WINDOW_TITLE);
        add(rootPanel);
        this.employee = employee;
        txtName.setText(this.employee.getName());
        txtEmail.setText(this.employee.getEmail());
        txtIcNum.setText(this.employee.getICnum());
        txtPhone.setText(this.employee.getPhone());
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!emptyValidation()) {
                        throw new Exception("Field is empty!");
                    }
                    String name = txtName.getText().trim();
                    String email = txtEmail.getText().trim();
                    String icNum = txtIcNum.getText().trim();
                    String phoneNum = txtPhone.getText().trim();
                    employee.setName(name);
                    employee.setEmail(email);
                    employee.setICnum(icNum);
                    employee.setPhone(phoneNum);
                    employee.setModifiedDate(new Date());
                    new EmployeeControl().updateEmployee(employee);
                    UiControl.mainFrame = new DashboardFrame();
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

    private boolean emptyValidation() throws Exception {
        AtomicBoolean isValid = new AtomicBoolean(false);
        if (txtName.getText().trim().isEmpty()
                || txtEmail.getText().trim().isEmpty()
                || txtIcNum.getText().trim().isEmpty()
                || txtPhone.getText().trim().isEmpty()) {
            throw new Exception("Profile's field is not entered.");
        } else {
            isValid.set(true);
        }

        return isValid.get();
    }

}

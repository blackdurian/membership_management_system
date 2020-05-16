package main.java.views.employee;

import main.java.controllers.EmployeeControl;
import main.java.views.UiControl;
import main.java.models.employees.Employee;
import main.java.views.users.UserCreateFrame;


import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class EmployeeCreateFrame extends JFrame {
    private JTextField txtPhone;
    private JTextField txtIcNum;
    private JTextField txtEmail;
    private JTextField txtName;
    private JButton btnNext;
    private JButton btnCancel;
    private JRadioButton managerRadioBtn;
    private JRadioButton staffRadioBtn;
    private JPanel rootPanel;
    private JLabel lblPhone;
    private JLabel lblName;
    private JLabel lblEmail;
    private JLabel lblIcNum;
    private JLabel lblRole;
    private JPanel pnlSubmit;
    private JLabel lblHeading;
    private ButtonGroup radioBtnUserRole;
    private Employee employee;

    public EmployeeCreateFrame() {
        super("Create Employee Account" + UiControl.WINDOW_TITLE);
        add(rootPanel);
        btnNext.addActionListener(e -> {
            try {
                if (emptyValidation()) {
                    String name = txtName.getText().trim();
                    String email = txtEmail.getText().trim();
                    String icNum = txtIcNum.getText().trim();
                    String phoneNum = txtPhone.getText().trim();
                    UserCreateFrame userCreateFrame = new UserCreateFrame();
                    if (staffRadioBtn.isSelected()) {
                        employee = new EmployeeControl().getNewStaff(name, icNum, email, phoneNum);
                    }
                    if (managerRadioBtn.isSelected()) {
                        employee = new EmployeeControl().getNewManager(name, icNum, email, phoneNum);
                    }
                    userCreateFrame.setEmployee(employee);
                    setVisible(false);
                    UiControl.thirdFrame = userCreateFrame;
                    UiControl.initSubFrame(UiControl.thirdFrame);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                ex.printStackTrace();
            }
        });
        btnCancel.addActionListener(e -> {
            UiControl.mainFrame.setVisible(true);
            dispose();
        });
    }
    private boolean emptyValidation() throws Exception {
        AtomicBoolean isValid = new AtomicBoolean(false);
        if (txtEmail.getText().trim().isEmpty()
                || txtName.getText().trim().isEmpty()
                || txtIcNum.getText().trim().isEmpty()
                || txtPhone.getText().trim().isEmpty()) {
            throw new Exception("Field is not entered.");
        } else {
            isValid.set(true);
        }
        if (radioBtnUserRole.getSelection() != null) {
            isValid.set(true);
        } else {
            throw new Exception("Role is not selected");
        }
        return isValid.get();
    }
}

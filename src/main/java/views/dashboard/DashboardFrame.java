package main.java.views.dashboard;

import main.java.controllers.*;
import main.java.models.employees.Employee;
import main.java.models.members.Member;
import main.java.models.members.Subscription;
import main.java.models.payments.Payment;
import main.java.views.UiControl;
import main.java.views.employee.EditProfileFrame;
import main.java.views.payment.PaymentRenewFrame;
import main.java.views.subscription.SubscriptionUpgradeFrame;
import main.java.views.users.ChangePasswordFrame;
import main.java.views.users.LoginFrame;
import main.java.views.subscription.SubscriptionFrame;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.*;


public class DashboardFrame extends JFrame {
    private JButton btnPrintReceipt;
    private JButton btnAddSub;
    private JButton btnSetRenewalSub;
    private JButton btnUpgradeSub;
    private JButton btnRenewSub;
    private JButton btnAddMember;
    private JButton btnDeleteMember;
    private JButton btnUpdateMember;
    private JButton btnPrintReport;
    private JComboBox cboAccount;
    private JLabel lblUserdetail;
    private JLabel lblPackage;
    private JLabel lblPrice;
    private JLabel lblRenewDate;
    private JLabel lblStatus;
    private JLabel lblExpiredDate;
    private JLabel lblHeading;
    private JLabel lblLogo;
    private JLabel lblSelectedPayment;
    private JLabel lblSelectedMember;
    private JPanel paymentBodyPanel;
    private JPanel pnlHeading;
    private JPanel rootPanel;
    private JPanel reportTablePanel;
    private JPanel memberWrapper;
    private JPanel paymentControlPanel;
    private JPanel paymentTablePanel;
    private JPanel memberTab;
    private JPanel memberDetailPanel;
    private JPanel subscriptionPanel;
    private JPanel paymentTab;
    private JPanel reportTab;
    private JPanel memberTablePanel;
    private JScrollPane memberScrollPane;
    private JScrollPane receiptScrollPane;
    private JScrollPane reportScrollPane;
    private JScrollPane paymentScrollPane;
    private JTable memberTable;
    private JTable paymentTable;
    private JTable reportTable;
    private JTabbedPane tabbedMain;
    private JTextArea txtAreaReceipt;
    private JTextField txtMemberEmail;
    private JTextField txtMemberPhone;
    private JTextField txtMemberSearch;
    private JTextField txtMemberIC;
    private JTextField txtMemberName;
    private JTextField txtPaymentSearch;
    private JTextField txtReportSearch;
    private MemberTableModel memberTableModel;
    private PaymentTableModel paymentTableModel;
    private ReportTableModel reportTableModel;
    private int memberSelectedRow;
    private EmployeeControl employeeControl = new EmployeeControl();
    private MemberControl memberControl = new MemberControl();
    private PaymentControl paymentControl = new PaymentControl();
    private TableRowSorter rowSorter;
    private static final Logger LOGGER = Logger.getLogger(DashboardFrame.class.getName());
    private static Handler fileHandler = null;

    public DashboardFrame() {
        super("Dashboard" + UiControl.WINDOW_TITLE);
        initLogger();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int reply = JOptionPane.showConfirmDialog(
                        null
                        , "Do you really want to exit?"
                        , "Confirm Exit"
                        , JOptionPane.YES_NO_OPTION
                );
                if (reply == JOptionPane.YES_OPTION) {
                    if (fileHandler == null) {
                        initLogger();
                    }
                    LOGGER.info("System Exit");
                    LOGGER.removeHandler(fileHandler);
                    fileHandler.close();
                    System.exit(0);
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
        add(rootPanel);
        lblUserdetail.setText(Season.getUserType() + " - " + Season.getUsername());
        initMemberTable();
        initPaymentTable();
        if (Season.getUserType().equals("Manager")) {
            initReportTable();
        }
        if (Season.getUserType().equals("Staff")) {
            tabbedMain.remove(2); // Staff can't view then Report Tab
        }
        cboAccount.addActionListener(e -> {
            String selectedItem = cboAccount.getSelectedItem().toString();
            switch (selectedItem) {
                case "Account":
                    break;
                case "Edit Profile":
                    //TODO: logger
                    Employee employee = employeeControl.getEmployeeById(Season.getUserId());
                    DashboardFrame.this.setVisible(false);
                    fileHandler.close();
                    UiControl.secondFrame = new EditProfileFrame(employee);
                    UiControl.initSubFrame(UiControl.secondFrame);
                    break;
                case "Change Password":
                    fileHandler.close();
                    UiControl.secondFrame = new ChangePasswordFrame(Season.getUserId());
                    UiControl.initSubFrame(UiControl.secondFrame);
                    break;
                case "Sign out":
                    String logMessage = String.format("Sign Out: %s, Role: %s", Season.getUsername(), Season.getUserType());
                    if (fileHandler == null) {
                        initLogger();
                    }
                    LOGGER.info(logMessage);
                    fileHandler.close();
                    Season.setUser(null); // clear user
                    UiControl.mainFrame = new LoginFrame();
                    UiControl.initMainFrame(UiControl.mainFrame);
                    DashboardFrame.this.dispose();
                    break;
            }
        });
        //<editor-fold desc="Member Button">
        // member ADD Button
        btnAddMember.addActionListener(e -> {
            //TODO: VALIDATION no duplicated data added
            try {
                if (memberEmptyValidation()) {
                    String name = txtMemberName.getText().trim();
                    String email = txtMemberEmail.getText().trim();
                    String icNum = txtMemberIC.getText().trim();
                    String phone = txtMemberPhone.getText().trim();
                    memberControl.addNewMember(name, icNum, email, phone); // add to database
                    loadMemberTable(); // reload table
                    setMemberSelectedRow(memberTableModel.getRowCount() - 1);
                    memberTable.setRowSelectionInterval(memberSelectedRow, memberSelectedRow);
                    clearTextFields(memberDetailPanel);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                ex.printStackTrace();
            }
        });
        // member UPDATE Button
        btnUpdateMember.addActionListener(e -> {
            try {
                if (memberEmptyValidation()) {
                    memberSelectedRow = memberTable.getSelectedRow();
                    //todo:  no duplicate data
                    if (memberTable.getSelectedRow() != -1) {
                        Member selectedMember = memberTableModel.getMember(memberSelectedRow);
                        String id = selectedMember.getId();
                        String name = txtMemberName.getText().trim();
                        String email = txtMemberEmail.getText().trim();
                        String icNum = txtMemberIC.getText().trim();
                        String phone = txtMemberPhone.getText().trim();
                        memberControl.editMemberById(id, name, icNum, email, phone);
                        loadMemberTable();
                        memberTable.setRowSelectionInterval(memberSelectedRow, memberSelectedRow);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                ex.printStackTrace();
            }
        });
        // member DELETE Button
        btnDeleteMember.addActionListener(e -> {
            try {
                if (memberTable.getSelectedRow() != -1) {
                    Member selectedMember = memberTableModel.getMember(memberSelectedRow);
                    int reply = JOptionPane.showConfirmDialog(  // delete confirm prompt up
                            null
                            , "Are you sure that you want to permanently delete member " + selectedMember.getName()
                            , "Confirm Delete"
                            , JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        memberControl.deleteMemberById(selectedMember.getId());
                        loadMemberTable();
                        memberTable.setRowSelectionInterval(0, 0);
                    }
                } else {
                    throw new Exception("No member selected");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                //TODO: logger
            }
        });
        //</editor-fold>

        txtMemberSearch.getDocument().addDocumentListener(new DocumentListener() { // Search function in member's table
            public void changedUpdate(DocumentEvent e) {
                searchTable();
            }

            public void removeUpdate(DocumentEvent e) {
                searchTable();
            }

            public void insertUpdate(DocumentEvent e) {
                searchTable();
            }

            public void searchTable() {
                //TODO: filter List, update table
                if (txtMemberSearch.getText().trim().length() > 0) {
                    String searchString = txtMemberSearch.getText();
                    List<Member> memberList = memberControl.filterMemberList(searchString);
                    memberTableModel = new MemberTableModel(memberList);
                    memberTable.setModel(memberTableModel);
                } else {
                    loadMemberTable();
                }
            }
        });
        txtPaymentSearch.getDocument().addDocumentListener(new DocumentListener() { // Search function in member's table
            public void changedUpdate(DocumentEvent e) {
                searchTable();
            }

            public void removeUpdate(DocumentEvent e) {
                searchTable();
            }

            public void insertUpdate(DocumentEvent e) {
                searchTable();
            }

            public void searchTable() {
                //TODO: filter List, update table
                if (txtPaymentSearch.getText().trim().length() > 0) {
                    String searchString = txtPaymentSearch.getText();
                    List<Payment> paymentList = paymentControl.filterPaymentList(searchString);
                    paymentTableModel = new PaymentTableModel(paymentList);
                    paymentTable.setModel(paymentTableModel);
                } else {
                    loadPaymentTable();
                }
            }
        });
        txtReportSearch.getDocument().addDocumentListener(new DocumentListener() { // Search function in member's table
            public void changedUpdate(DocumentEvent e) {
                filter();
            }
            //TODO: Debug reload after change
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            public void filter() {
                //TODO: filter List, update table
                RowFilter<ReportTableModel, Object> rf = null;
                if (txtReportSearch.getText().trim().length() > 0) {
                    try {
                        rf = RowFilter.regexFilter(txtReportSearch.getText());
                    } catch (java.util.regex.PatternSyntaxException e) {
                        return;
                    }
                    rowSorter.setRowFilter(rf);

                }
            }
        });
        //<editor-fold desc="Subscription Button">
        btnAddSub.addActionListener(e -> {
            try {
                if (memberTable.getSelectedRow() != -1) {
                    Member selectedMember = memberTableModel.getMember(memberSelectedRow);
                    setVisible(false);
                    fileHandler.close();
                    UiControl.secondFrame = new SubscriptionFrame(selectedMember);
                    UiControl.initSubFrame(UiControl.secondFrame);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                //TODO: logger
            }
        });
        btnSetRenewalSub.addActionListener(e -> {
            try {
                memberSelectedRow = memberTable.getSelectedRow();
                if (memberTable.getSelectedRow() != -1) {
                    Member selectedMember = memberTableModel.getMember(memberSelectedRow);
                    if (selectedMember.getSubscription() != null) {
                        Subscription selectedSub = selectedMember.getSubscription();
                        memberControl.setRenewalByMemberId(!selectedSub.getAutoRenew(), selectedMember.getId());
                        loadMemberTable();
                        memberTable.setRowSelectionInterval(memberSelectedRow, memberSelectedRow);
                    } else {
                        JOptionPane.showMessageDialog(null, "This Member has no subscription, please ADD a new one.");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                //TODO LOGGER:
            }


        });
        btnUpgradeSub.addActionListener(e -> {
            try {
                if (memberTable.getSelectedRow() != -1) {
                    Member selectedMember = memberTableModel.getMember(memberSelectedRow);
                    if (selectedMember.getSubscription() != null) {
                        setVisible(false);
                        fileHandler.close();
                        UiControl.secondFrame = new SubscriptionUpgradeFrame(selectedMember);
                        UiControl.initSubFrame(UiControl.secondFrame);
                    } else {
                        JOptionPane.showMessageDialog(null, "This Member has no subscription, please ADD a new one.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select member on table.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                //TODO: logger
            }
        });
        btnRenewSub.addActionListener(e -> {
            try {
                if (memberTable.getSelectedRow() != -1) {
                    Member selectedMember = memberTableModel.getMember(memberSelectedRow);
                    if (selectedMember.getSubscription() != null) {
                        Subscription selectedSub = selectedMember.getSubscription();
                        setVisible(false);
                        fileHandler.close();
                        UiControl.secondFrame = new PaymentRenewFrame(selectedMember, selectedSub, selectedSub.getPrice());
                        UiControl.initSubFrame(UiControl.secondFrame);
                    } else {
                        JOptionPane.showMessageDialog(null, "This Member has no subscription, please ADD a new one.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select member on table.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                //TODO: logger
            }
        });
        //</editor-fold>
        btnPrintReceipt.addActionListener(e -> {
            try {
                if (txtAreaReceipt.getText().isEmpty()) {
                    throw new Exception("Receipt is empty.");
                }
                String printText[] = txtAreaReceipt.getText().split("\\r?\\n");
                ReportControl reportControl = new ReportControl();
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                chooser.setDialogTitle("Save Receipt as");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String path = chooser.getSelectedFile().getAbsolutePath() + "\\SubscriptionReceipt.pdf";
                    reportControl.printPDF(printText, path);
                } else {
                    System.out.println("No Selection ");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, e1.getMessage());
                //TODO: LOGGER
            }
        });

        btnPrintReport.addActionListener(e -> {
            try {
                boolean complete = reportTable.print();
                if (complete) {
                    // show a success message
                    JOptionPane.showMessageDialog(null, "Print Successful");
                } else {
                    //show a message indicating that printing was cancelled
                    JOptionPane.showMessageDialog(null, "Print Cancelled");
                }
            } catch (PrinterException pe) {
                //Printing failed, report to the user
                JOptionPane.showMessageDialog(null, pe.getMessage());
            }
        });
    }

    private boolean memberEmptyValidation() throws Exception {
        AtomicBoolean isValid = new AtomicBoolean(false);
        if (txtMemberName.getText().trim().isEmpty()
                || txtMemberEmail.getText().trim().isEmpty()
                || txtMemberIC.getText().trim().isEmpty()
                || txtMemberPhone.getText().trim().isEmpty()) {
            throw new Exception("Member's field is not entered.");
        } else {
            isValid.set(true);
        }
        return isValid.get();
    }

    private void loadMemberTable() {
        memberTableModel = new MemberTableModel(memberControl.getAllMember());
        memberTable.setModel(memberTableModel);
    }

    private void initMemberTable() {
        loadMemberTable();
        memberTable.createDefaultColumnsFromModel();
        memberScrollPane.setViewportView(memberTable);
        memberTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        memberTable.getSelectionModel().addListSelectionListener(e -> {
            if (memberTable.getSelectedRow() != -1) {
                memberSelectedRow = memberTable.getSelectedRow();
                Member selectedMember = memberTableModel.getMember(memberSelectedRow); // get selected member
                lblSelectedMember.setText("Selected : " + selectedMember.getName());
                txtMemberName.setText(selectedMember.getName());
                txtMemberIC.setText(selectedMember.getICnum());
                txtMemberEmail.setText(selectedMember.getEmail());
                txtMemberPhone.setText(selectedMember.getPhone());
                if (selectedMember.getSubscription() != null) {
                    Subscription SelectedSub = selectedMember.getSubscription();
                    Date expiredDate = SelectedSub.getExpiredDate();
                    String untilDate = Season.DATE_FORMAT.format(expiredDate);
                    String priceTag = String.format("%s %.2f", "RM", SelectedSub.getPrice());
                    String packageTemp = SelectedSub.getPlan();
                    String packageText = packageTemp.substring(0, 1).toUpperCase() + packageTemp.substring(1);
                    String autoRenewal;
                    if (SelectedSub.getAutoRenew()) {
                        autoRenewal = "On";
                        btnSetRenewalSub.setText("Set Renewal Off");
                    } else {
                        autoRenewal = "Off";
                        btnSetRenewalSub.setText("Set Renewal On");
                    }
                    String renewDate = String.format("%s - Renewal: %s", Season.DATE_FORMAT.format(SelectedSub.getRenewDate()), autoRenewal);
                    lblPackage.setText(packageText);
                    lblExpiredDate.setText(untilDate);
                    lblRenewDate.setText(renewDate);
                    lblPrice.setText(priceTag);
                    if (expiredDate.compareTo(new Date()) >= 0) {
                        lblStatus.setText("Active");
                    } else {
                        lblStatus.setText("Expired");
                    }
                } else {
                    lblPackage.setText("");
                    lblExpiredDate.setText("");
                    lblRenewDate.setText("");
                    lblPrice.setText("");
                    lblStatus.setText("");
                }
            } else {
                lblSelectedMember.setText("No Selected Member ");
            }
        });
    }

    private void loadPaymentTable() {
        paymentTableModel = new PaymentTableModel(paymentControl.getAllPayment());
        paymentTable.setModel(paymentTableModel);
    }

    private void initPaymentTable() {
        loadPaymentTable();
        paymentTable.createDefaultColumnsFromModel();
        paymentScrollPane.setViewportView(paymentTable);
        paymentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        paymentTable.getSelectionModel().addListSelectionListener(e -> {
            if (paymentTable.getSelectedRow() != -1) {
                int paymentSelectedRow = paymentTable.getSelectedRow(); //get selected row
                Payment selectedPayment = paymentTableModel.getPayment(paymentSelectedRow); // get selected payment
                lblSelectedPayment.setText("Selected : " + selectedPayment.getId());
                Member selectedMember = memberControl.getMemberById(selectedPayment.getMemberId());
                String paymentDateText = Season.DATE_FORMAT.format(selectedPayment.getCreatedDate());
                String amount = String.format("%.2f", selectedPayment.getAmount());
                String payerName = selectedMember.getName();
                String payeeName = "";
                if (selectedPayment.getEmployeeId() != null) {
                    payeeName = employeeControl.getEmployeeById(selectedPayment.getEmployeeId()).getName();
                }
                String receiptText = "RECEIPT"
                        + "\n\n Date: \t\t\t" + paymentDateText
                        + "\n Receipt No: \t\t" + selectedPayment.getId()
                        + "\n\n Received From: \t" + payerName
                        + "\n Amount: \t\tRM " + amount
                        + "\n Item: \t\t\tSubscription " + selectedPayment.getItem()
                        + "\n Receipt By: \t\t" + payeeName
                        + "\n Memo: \t\t" + selectedPayment.getMemo();

                txtAreaReceipt.setTabSize(4);
                txtAreaReceipt.setText(receiptText);
            } else {
                lblSelectedPayment.setText("No Selected Member ");
            }
        });
    }

    private void initReportTable() {
        reportTableModel = new ReportTableModel(memberControl.getAllMember());
        rowSorter = new TableRowSorter<>(reportTableModel);
        reportTable.setModel(reportTableModel);
        reportTable.setRowSorter(rowSorter);
        reportTable.createDefaultColumnsFromModel();
        reportScrollPane.setViewportView(reportTable);
        reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private static void initLogger() {
        try {
            fileHandler = new FileHandler(PathConfig.LOGGER_PATH, true); // Creating FileHandler
            fileHandler.setLevel(Level.ALL); // Setting Level to ALL
            fileHandler.setFormatter(new SimpleFormatter());   // Setting formatter to the handler
            LOGGER.addHandler(fileHandler); // Assigning handler to logger
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", e);
        }
    }

    private void clearTextFields(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JTextField) {
                JTextField f = (JTextField) c;
                f.setText("");
            }
        }
    }

    private void setMemberSelectedRow(int memberSelectedRow) {
        this.memberSelectedRow = memberSelectedRow;
    }

    public void selectLastPaymentRow() {
        int index = paymentTableModel.getRowCount() - 1;
        this.paymentTable.setRowSelectionInterval(index, index);
    }

    public void setTabbedSelectedIndex(int index) {
        tabbedMain.setSelectedIndex(index);
    }
}

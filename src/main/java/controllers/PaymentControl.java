package main.java.controllers;

import com.google.gson.Gson;
import main.java.models.payments.Payment;
import main.java.models.payments.PaymentRepo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PaymentControl {

    private String PATH = PathConfig.PAYMENT_REPO_PATH;
    private PaymentRepo paymentRepoObject;

    public PaymentControl() {
        deserialize();
    }

    public Payment getLatestPaymentByMemberId(String memberId) {
        List<Payment> paymentList = new ArrayList<>();
        Payment result = new Payment();
        try {
            for (Payment payment : this.paymentRepoObject.getPaymentList()) {
                if (payment.getMemberId() != null) {
                    if (payment.getMemberId().equals(memberId)) {
                        paymentList.add(payment);
                    }
                }
            }
            if (paymentList.size() != 0) {
                Collections.sort(paymentList);
                result = paymentList.get(0);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addNewPayment(Boolean transactionStatus, Double amount, String memo, String item, String memberId, String employeeId) {
        Payment payment = new Payment(new Date(), transactionStatus, amount, UUID.randomUUID().toString(), memo, item, memberId, employeeId);
        payment.setModifiedDate(new Date());
        List<Payment> paymentList = this.paymentRepoObject.getPaymentList(); // get exist staff list
        paymentList.add(payment);
        this.paymentRepoObject.setPaymentList(paymentList);
        SaveToFile(this.paymentRepoObject);
    }

    public void deletePaymentById(String id) throws Exception {
        List<Payment> paymentList = new ArrayList<>();
        boolean isFound = false;
        for (Payment payment : this.paymentRepoObject.getPaymentList()) {
            if (payment.getId().equals(id)) {
                isFound = true;
                continue;
            }
            paymentList.add(payment);
        }   //Save delete if found
        if (isFound) {
            this.paymentRepoObject.setPaymentList(paymentList);
            SaveToFile(this.paymentRepoObject);
        } else {
            throw new Exception("No ID Found");

        }
    }

    public void editPaymentById(String id, Boolean transactionStatus, Double amount, String memo, String item, String memberId, String employeeId) {
        List<Payment> paymentList = new ArrayList<>();
        for (Payment payment : this.paymentRepoObject.getPaymentList()) {
            if (payment.getId().equals(id)) {
                payment.setTransactionStatus(transactionStatus);
                payment.setAmount(amount);
                payment.setMemo(memo);
                payment.setItem(item);
                payment.setMemberId(memberId);
                payment.setEmployeeId(employeeId);
                payment.setModifiedDate(new Date());
                paymentList.add(payment);
            } else {
                paymentList.add(payment);
            }
        }
        this.paymentRepoObject.setPaymentList(paymentList);
        SaveToFile(this.paymentRepoObject);
    }

    public List<Payment> filterPaymentList(String searchText) {
        List<Payment> paymentList = new ArrayList<>();

        try {
            for (Payment payment : this.paymentRepoObject.getPaymentList()) {
                if (payment.toString().contains(searchText)) {
                    paymentList.add(payment);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return paymentList;
    }

    public List<Payment> getAllPayment() {
        return this.paymentRepoObject.getPaymentList();
    }

    public Payment getPaymentById(String id) {
        Payment result = new Payment();
        try {
            for (Payment payment : this.paymentRepoObject.getPaymentList()) {
                if (payment.getId().equals(id)) {
                    result = payment;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void SaveToFile(PaymentRepo paymentRepo) {
        Gson gson = new Gson();
        String EmployeeJson = gson.toJson(paymentRepo); // write serialized
        try {
            FileWriter fileWriter = new FileWriter(PATH); //  Assume default encoding
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(EmployeeJson);
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void deserialize() {
        String json = readAll(PATH);
        Gson gson = new Gson();
        //read deserialize
        this.paymentRepoObject = gson.fromJson(json, PaymentRepo.class);
        //if file empty or no exist, initial new file
        if (this.paymentRepoObject == null) {
            initNewFile();
        }
    }

    private void initNewFile() {
        List<Payment> paymentList = new ArrayList<>();
        this.paymentRepoObject = new PaymentRepo(paymentList);
        SaveToFile(this.paymentRepoObject);
    }

    private static String readAll(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

}

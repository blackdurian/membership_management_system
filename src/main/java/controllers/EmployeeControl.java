package main.java.controllers;

import com.google.gson.Gson;
import main.java.models.employees.Employee;
import main.java.models.employees.EmployeeRepo;
import main.java.models.employees.Manager;
import main.java.models.employees.Staff;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class EmployeeControl {
    private String PATH = PathConfig.EMPLOYEE_REPO_PATH;
    private EmployeeRepo employeeRepoObject;

    public EmployeeControl() {
        deserialize();
    }

    public Staff getNewStaff(String name, String ICnum, String email, String phone) {
        Staff staff = new Staff(UUID.randomUUID().toString(), name, ICnum, email, phone);
        staff.setCreatedDate(new Date());
        staff.setModifiedDate(new Date());
        return staff;
    }

    public Manager getNewManager(String name, String ICnum, String email, String phone) {
        Manager manager = new Manager(UUID.randomUUID().toString(), name, ICnum, email, phone);
        manager.setCreatedDate(new Date());
        manager.setModifiedDate(new Date());
        return manager;
    }

    public void addNewStaff(Staff staff) {
        List<Staff> staffList = this.employeeRepoObject.getStaffList();
        staffList.add(staff);
        this.employeeRepoObject.setStaffList(staffList);
        SaveToFile(this.employeeRepoObject);
    }

    public void addNewStaff(String name, String ICnum, String email, String phone) {
        Staff staff = new Staff(UUID.randomUUID().toString(), name, ICnum, email, phone);
        staff.setCreatedDate(new Date());
        staff.setModifiedDate(new Date());
        //get exist staff list
        List<Staff> staffList = this.employeeRepoObject.getStaffList();
        staffList.add(staff);
        this.employeeRepoObject.setStaffList(staffList);
        SaveToFile(this.employeeRepoObject);
    }

    public void addNewManager(Manager manager) {
        List<Manager> managerList = this.employeeRepoObject.getManagerList();
        managerList.add(manager);
        this.employeeRepoObject.setManagerList(managerList);
        SaveToFile(this.employeeRepoObject);
    }

    public void addNewManager(String name, String ICnum, String email, String phone) {
        Manager manager = new Manager(UUID.randomUUID().toString(), name, ICnum, email, phone);
        manager.setCreatedDate(new Date());
        manager.setModifiedDate(new Date());
        List<Manager> managerList = this.employeeRepoObject.getManagerList(); // get exist manager list
        managerList.add(manager);
        this.employeeRepoObject.setManagerList(managerList);
        SaveToFile(this.employeeRepoObject);
    }

    public void deleteManagerById(String id) throws Exception {
        List<Manager> managerList = new ArrayList<>();
        boolean isFound = false;
        for (Manager manager : this.employeeRepoObject.getManagerList()) {
            if (manager.getID().equals(id)) { // if found then skip adding
                isFound = true;
                continue;
            }
            managerList.add(manager);
        }
        if (isFound) { // Save delete if found
            this.employeeRepoObject.setManagerList(managerList);
            SaveToFile(this.employeeRepoObject);
        } else {
            throw new Exception("No id Found");
        }
    }

    public void deleteStaffById(String id) throws Exception {
        List<Staff> staffList = new ArrayList<>();
        boolean isFound = false;
        for (Staff staff : this.employeeRepoObject.getStaffList()) {
            if (staff.getID().equals(id)) {
                isFound = true;
                continue;
            }
            staffList.add(staff);
        }
        if (isFound) { // Throw exception if not found
            this.employeeRepoObject.setStaffList(staffList);
            SaveToFile(this.employeeRepoObject);
        } else {
            throw new Exception("No id Found");
        }
    }

    public void updateEmployee(Employee employee) {
        try {
            if (employee instanceof Manager) {
                List<Manager> managerList = new ArrayList<>();
                for (Manager manager : this.employeeRepoObject.getManagerList()) {
                    if (manager.getID().equals(employee.getID())) {
                        managerList.add((Manager) employee);
                    } else {
                        managerList.add(manager);
                    }
                }
                this.employeeRepoObject.setManagerList(managerList);
            }
            if (employee instanceof Staff) {
                List<Staff> staffList = new ArrayList<>();
                for (Staff staff : this.employeeRepoObject.getStaffList()) {
                    if (staff.getID().equals(employee.getID())) {
                        staffList.add((Staff) employee);
                    } else {
                        staffList.add(staff);
                    }
                }
                this.employeeRepoObject.setStaffList(staffList);
            }
            SaveToFile(this.employeeRepoObject);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void editStaffById(String id, String name, String ICnum, String email, String phone) {
        List<Staff> staffList = new ArrayList<>();
        this.employeeRepoObject.getStaffList().forEach(staff -> { if (staff.getID().equals(id)) {
            staff.setName(name);
            staff.setICnum(ICnum);
            staff.setPhone(phone);
            staff.setEmail(email);
            staff.setModifiedDate(new Date());
            staffList.add(staff);
        } else {
            staffList.add(staff);
        }});

        this.employeeRepoObject.setStaffList(staffList);
        SaveToFile(this.employeeRepoObject);
    }

    public void editManagerById(String id, String name, String ICnum, String email, String phone) {
        List<Manager> managerList = new ArrayList<>();
        for (Manager manager : this.employeeRepoObject.getManagerList()) {
            if (manager.getID().equals(id)) {
                manager.setName(name);
                manager.setICnum(ICnum);
                manager.setEmail(email);
                manager.setPhone(phone);
                manager.setModifiedDate(new Date());
                managerList.add(manager);
            } else {
                managerList.add(manager);
            }
        }
        this.employeeRepoObject.setManagerList(managerList);
        SaveToFile(this.employeeRepoObject);
    }

    public Employee getEmployeeById(String id) {
        Employee employee = null;
        try {
            for (Manager manager : this.employeeRepoObject.getManagerList()) {
                if (manager.getID().equals(id)) {
                    employee = manager;
                }
            }
            for (Staff staff : this.employeeRepoObject.getStaffList()) {
                if (staff.getID().equals(id)) {
                    employee = staff;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public List<Manager> getAllManager() {
        return this.employeeRepoObject.getManagerList();
    }

    public List<Staff> getAllStaff() {
        return this.employeeRepoObject.getStaffList();
    }

    public Manager getManagerById(String id) {
        Manager result = null;
        try {
            for (Manager manager : this.employeeRepoObject.getManagerList()) {
                if (manager.getID().equals(id)) {
                    result = manager;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Staff getStaffById(String id) {
        Staff result = null;
        try {
            for (Staff staff : this.employeeRepoObject.getStaffList()) {
                if (staff.getID().equals(id)) {
                    result = staff;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void SaveToFile(EmployeeRepo employeeRepo) {
        Gson gson = new Gson();
        String EmployeeJson = gson.toJson(employeeRepo); // write serialized
        try {  //save to file
            FileWriter fileWriter = new FileWriter(PATH); // Assume default encoding.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); // Always wrap FileWriter in BufferedWriter.
            bufferedWriter.write(EmployeeJson);
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<Manager> searchManager(String searchText) {
        List<Manager> result = new ArrayList<>();
        for (Manager manager : this.employeeRepoObject.getManagerList()) {
            if (manager.isFound(searchText)) {
                result.add(manager);
            }
        }
        return result;
    }

    public Staff searchStaff(String searchText) {
        Staff result = null;
        for (Staff staff : this.employeeRepoObject.getStaffList()) {
            if (staff.isFound(searchText)) {
                result = staff;
            }
        }
        return result;
    }

    private void deserialize() {
        String json = readAll(PATH);
        Gson gson = new Gson();
        this.employeeRepoObject = gson.fromJson(json, EmployeeRepo.class); // read deserialize
        if (this.employeeRepoObject == null) { // if file empty or no exist, initial new file
            initNewFile();
        }
    }

    private void initNewFile() {
        List<Manager> managerList = new ArrayList<>();
        List<Staff> staffList = new ArrayList<>();
        this.employeeRepoObject = new EmployeeRepo(staffList, managerList);
        SaveToFile(this.employeeRepoObject);
    }

    private String readAll(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

}

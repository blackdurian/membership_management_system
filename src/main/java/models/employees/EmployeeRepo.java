package main.java.models.employees;

import java.util.List;

public class EmployeeRepo {

    private List<Staff> staffList;
    private List<Manager> managerList;

    public EmployeeRepo(List<Staff> staffList, List<Manager> managerList) {
        this.staffList = staffList;
        this.managerList = managerList;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public List<Manager> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<Manager> managerList) {
        this.managerList = managerList;
    }

    public int getStaffCount(){
        return staffList.size();
    }

}

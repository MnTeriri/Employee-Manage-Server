package model;

import com.alibaba.fastjson2.annotation.JSONField;

import java.time.LocalDateTime;

public class Employee {
    private Integer id;
    private String eno;
    private String ename;
    private String sex;
    private Integer age;
    private String dept;//部门
    private String appointment;//职务
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime entryTime;//入职时间
    private String qualification;//学历
    private String imageString;
    private String phone;

    public Employee() {
    }

    public Employee(String eno, String ename, String sex, Integer age, String dept, String appointment, LocalDateTime entryTime, String qualification, String imageString, String phone) {
        this.eno = eno;
        this.ename = ename;
        this.sex = sex;
        this.age = age;
        this.dept = dept;
        this.appointment = appointment;
        this.entryTime = entryTime;
        this.qualification = qualification;
        this.imageString = imageString;
        this.phone = phone;
    }

    public Employee(Integer id, String eno, String ename, String sex, Integer age, String dept, String appointment, LocalDateTime entryTime, String qualification, String imageString, String phone) {
        this.id = id;
        this.eno = eno;
        this.ename = ename;
        this.sex = sex;
        this.age = age;
        this.dept = dept;
        this.appointment = appointment;
        this.entryTime = entryTime;
        this.qualification = qualification;
        this.imageString = imageString;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEno() {
        return eno;
    }

    public void setEno(String eno) {
        this.eno = eno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", eno='" + eno + '\'' +
                ", ename='" + ename + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", dept='" + dept + '\'' +
                ", appointment='" + appointment + '\'' +
                ", entryTime=" + entryTime +
                ", qualification='" + qualification + '\'' +
                ", imageString='" + imageString + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

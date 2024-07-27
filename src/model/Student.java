package model;

import java.util.Date;

public class Student {
    private String studentId;
    private String fullNAme;
    private Date dateOfBirth;
    private String address;

    public Student() {
    }

    public Student(String studentId, String fullNAme, Date dateOfBirth, String address) {
        this.studentId = studentId;
        this.fullNAme = fullNAme;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFullNAme() {
        return fullNAme;
    }

    public void setFullNAme(String fullNAme) {
        this.fullNAme = fullNAme;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", fullNAme='" + fullNAme + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                '}';
    }
}

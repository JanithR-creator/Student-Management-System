package controller;

import db.Database;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Student;

import java.time.ZoneId;
import java.util.Date;

public class StudentFormController {
    public AnchorPane context;
    public TextField txtId;
    public TextField txtName;
    public DatePicker txtDOB;
    public TextField txtAddress;
    public TextField txtSearch;

    public void initialize() {
        setStudentId();
    }

    private void setStudentId() {
        if (!Database.studentTable.isEmpty()) {
            Student lastStudent = Database.studentTable.get(
                    Database.studentTable.size() - 1
            );
            String lastId = lastStudent.getStudentId();
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsAString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            lastIntegerIdAsInt++;
            String generatedStudentId = "S-" + lastIntegerIdAsInt;
            txtId.setText(generatedStudentId);
        } else {
            txtId.setText("S-1");
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        Student student = new Student(
                txtId.getText(),
                txtName.getText(),
                Date.from(txtDOB.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                txtAddress.getText()
        );
        Database.studentTable.add(student);
        setStudentId();
        new Alert(Alert.AlertType.INFORMATION, "Student Saved!").show();
    }

    private void clear(){
        txtDOB.setValue(null);
        txtName.clear();
        txtAddress.clear();
    }
}

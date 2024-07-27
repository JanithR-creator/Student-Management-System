package controller;

import db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Student;
import view.tm.StudentTm;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

public class StudentFormController {
    public AnchorPane context;
    public TextField txtId;
    public TextField txtName;
    public DatePicker txtDOB;
    public TextField txtAddress;
    public TextField txtSearch;
    public TableView<StudentTm> tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDOB;
    public TableColumn colAddress;
    public TableColumn colOption;

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        setStudentId();
        setTableData();
    }

    private void setTableData() {
        ObservableList<StudentTm> obList= FXCollections.observableArrayList();
        for(Student st:Database.studentTable){
            Button btn = new Button("Delete");
            StudentTm tm = new StudentTm(
                    st.getStudentId(),
                    st.getFullNAme(),
                    new SimpleDateFormat("yyyy-MM-dd").format(st.getDateOfBirth()),
                    st.getAddress(),
                    btn
            );
            obList.add(tm);
        }
        tblStudent.setItems(obList);
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
        clear();
        setTableData();
        new Alert(Alert.AlertType.INFORMATION, "Student Saved!").show();
    }

    private void clear(){
        txtDOB.setValue(null);
        txtName.clear();
        txtAddress.clear();
    }
}

package controller;

import db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Student;
import view.tm.StudentTm;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

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
    public Button btn;
    String searchText = "";

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        setStudentId();
        setTableData(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            setTableData(searchText);
        });

        tblStudent.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (null != newValue) {
                        setData(newValue);
                    }
                });
    }

    private void setData(StudentTm tm) {
        txtId.setText(tm.getId());
        txtName.setText(tm.getFullName());
        txtAddress.setText(tm.getAddress());
        txtDOB.setValue(LocalDate.parse(tm.getDob(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        btn.setText("Update Student");
    }

    private void setTableData(String searchText) {
        ObservableList<StudentTm> obList = FXCollections.observableArrayList();
        for (Student st : Database.studentTable) {

            if (st.getFullNAme().contains(searchText)) {
                Button btn = new Button("Delete");
                StudentTm tm = new StudentTm(
                        st.getStudentId(),
                        st.getFullNAme(),
                        new SimpleDateFormat("yyyy-MM-dd").format(st.getDateOfBirth()),
                        st.getAddress(),
                        btn
                );
                btn.setOnAction(e -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Are you sure?",
                            ButtonType.YES,
                            ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {
                        Database.studentTable.remove(st);
                        new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
                        setTableData(searchText);
                        setStudentId();
                    }
                });
                obList.add(tm);
            }
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

        if (btn.getText().equalsIgnoreCase("Save Student")) {
            Student student = new Student(
                    txtId.getText(),
                    txtName.getText(),
                    Date.from(txtDOB.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    txtAddress.getText()
            );
            Database.studentTable.add(student);
            setStudentId();
            clear();
            setTableData(searchText);
            new Alert(Alert.AlertType.INFORMATION, "Student Saved!").show();
        } else {
            for (Student st : Database.studentTable) {
                if (st.getStudentId().equals(txtId.getText())) {
                    st.setAddress(txtAddress.getText());
                    st.setDateOfBirth(Date.from(txtDOB.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    st.setFullNAme(txtName.getText());
                    setTableData(searchText);
                    clear();
                    setStudentId();
                    btn.setText("Save Student");
                    return;
                }
            }
            new Alert(Alert.AlertType.WARNING, "Not Found").show();
        }
    }

    private void clear() {
        txtDOB.setValue(null);
        txtName.clear();
        txtAddress.clear();
    }

    public void backHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void newStudentOnAction(ActionEvent actionEvent) {
        clear();
        setStudentId();
        btn.setText("Save Student");
    }

    public void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}

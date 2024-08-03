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
import model.Program;
import model.Teacher;
import view.tm.TechAddTm;

import java.io.IOException;
import java.util.ArrayList;

public class ProgramFormController {
    public ComboBox<String> cmbTeacher;

    public TableColumn<?, ?> colCost;

    public TableColumn<?, ?> colId;

    public TableColumn<?, ?> colName;

    public TableColumn<?, ?> colOption;

    public TableColumn<?, ?> colTCode;

    public TableColumn<?, ?> colTNAme;

    public TableColumn<?, ?> colTRemove;

    public TableColumn<?, ?> colTeacher;

    public TableColumn<?, ?> colcolTechOption;

    public AnchorPane context;

    public TableView<?> tblPrograms;

    public TableView<TechAddTm> tblTechnologies;

    public TextField txtCost;

    public TextField txtId;

    public TextField txtName;

    public TextField txtSearch;

    public TextField txtTechnology;


    public void initialize() {
        setProgramCode();
        setTeachers();

        colTCode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        colTNAme.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colTRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    ObservableList<TechAddTm> tmList = FXCollections.observableArrayList();

    ArrayList<String> teachersArray = new ArrayList<>();

    private void setTeachers() {
        for (Teacher t : Database.teacherTable) {
            teachersArray.add(t.getCode() + ". " + t.getName());
        }
        ObservableList<String> observableList = FXCollections.observableList(teachersArray);
        cmbTeacher.setItems(observableList);
    }

    private void setProgramCode() {
        if (!Database.programTable.isEmpty()) {
            Program lastProgram = Database.programTable.get(
                    Database.programTable.size() - 1
            );
            String lastId = lastProgram.getCode();
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsAString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            lastIntegerIdAsInt++;
            String generatedStudentId = "P-" + lastIntegerIdAsInt;
            txtId.setText(generatedStudentId);
        } else {
            txtId.setText("P-1");
        }
    }

    public void backHomeOnAction(ActionEvent event) throws IOException {
        setUi("DashboardForm");

    }

    public void newProgramOnAction(ActionEvent event) {

    }

    public void saveOnAction(ActionEvent event) {

    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void addTechOnAction(ActionEvent actionEvent) {
        if (!isExists(txtTechnology.getText().trim())) {
            Button btn = new Button("Remove");
            TechAddTm tm = new TechAddTm(
                    tmList.size() + 1, txtTechnology.getText().trim(), btn
            );
            tmList.add(tm);
            tblTechnologies.setItems(tmList);
            txtTechnology.clear();
        } else {
            txtTechnology.selectAll();
            new Alert(Alert.AlertType.WARNING, "Already Exist").show();
        }
    }

    private boolean isExists(String tech) {
        for (TechAddTm tm : tmList) {
            if (tm.getName().equals(tech)) {
                return true;
            }
        }
        return false;
    }
}

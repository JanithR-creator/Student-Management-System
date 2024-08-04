package controller;

import db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Intake;
import model.Program;

import java.io.IOException;
import java.util.ArrayList;

public class IntakeFormController {
    public TextField txtId;
    public TextField txtContact;
    public TextField txtSearch;
    public Button btn;
    public TableView tblTeacher;
    public TableColumn colId;
    public TableColumn colIntake;
    public TableColumn colDate;
    public TableColumn colProgram;
    public TableColumn colSate;
    public TableColumn colOption;
    public DatePicker txtDate;
    public AnchorPane context;
    public ComboBox cmbProgram;

    ArrayList<String> programs = new ArrayList<>();

    public void initialize(){
        setIntakeId();
        setProgram();
    }

    public void setProgram(){
        for (Program p : Database.programTable){
            programs.add(p.getCode()+". "+p.getName());
        }
        ObservableList<String> observableList= FXCollections.observableList(programs);
        cmbProgram.setItems(observableList);
    }

    public void setIntakeId(){
        if(!Database.intakeTable.isEmpty()){
            Intake lastIntake = Database.intakeTable.get(
                    Database.intakeTable.size()-1
            );
            String lastId=lastIntake.getProgramId();
            String splitData[]=lastId.split("-");
            String lastIdIntegerNumberAsAString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            lastIntegerIdAsInt++;
            String generatedIntakeId="I-"+lastIntegerIdAsInt;
            txtId.setText(generatedIntakeId);
        }else{
            txtId.setText("I-1");
        }
    }

    public void backHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void newTeacherOnAction(ActionEvent actionEvent) {
    }

    public void saveOnAction(ActionEvent actionEvent) {

    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}

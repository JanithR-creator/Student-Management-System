package controller;

import db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Intake;
import model.Program;
import model.Registration;

import java.io.IOException;
import java.util.ArrayList;

public class RegistrationFormController {
    public AnchorPane context;
    public TextField txtId;
    public TextField txtStudent;
    public ComboBox<String> cmbProgram;
    public Button btn;
    public RadioButton rBtnPaid;

    ArrayList<String> programs = new ArrayList<>();

    public void initialize() {
        setIntakeId();
        setPrograms();
    }

    public void setPrograms() {
        for (Program p : Database.programTable) {
            programs.add(p.getCode() + ". " + p.getName());
        }
        ObservableList<String> obList = FXCollections.observableArrayList(programs);
        cmbProgram.setItems(obList);
    }

    public void setIntakeId() {
        if (!Database.intakeTable.isEmpty()) {
            Intake lastIntake = Database.intakeTable.get(
                    Database.intakeTable.size() - 1
            );
            String lastId = lastIntake.getProgramId();
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsAString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            lastIntegerIdAsInt++;
            String generatedIntakeId = "R-" + lastIntegerIdAsInt;
            txtId.setText(generatedIntakeId);
        } else {
            txtId.setText("R-1");
        }
    }

    public void backHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void registerOnAction(ActionEvent actionEvent) {
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}

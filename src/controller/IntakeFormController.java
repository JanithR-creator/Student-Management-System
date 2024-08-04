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
import model.Intake;
import model.Program;
import view.tm.IntakeTm;
import view.tm.ProgramTm;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class IntakeFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtSearch;
    public Button btn;
    public TableView<IntakeTm> tblIntake;
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colIntake;
    public TableColumn<?, ?> colDate;
    public TableColumn<?, ?> colProgram;
    public TableColumn<?, ?> colOption;
    public DatePicker txtDate;
    public AnchorPane context;
    public ComboBox<String> cmbProgram;

    ArrayList<String> programs = new ArrayList<>();

    public void initialize(){
        setIntakeId();
        setProgram();


        colId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colIntake.setCellValueFactory(new PropertyValueFactory<>("intakeName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("pName"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
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
        Intake intake = new Intake(
                cmbProgram.getValue().split("\\.")[0],
                Date.from(txtDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                txtName.getText(),
                true,
                txtId.getId()
        );
        Database.intakeTable.add(intake);
        setIntakeId();
        clear();
        loadTableData();
        new Alert(Alert.AlertType.INFORMATION, "Intake Saved!").show();
    }

    private void clear() {
        txtDate.setValue(null);
        txtName.clear();
        cmbProgram.setValue(null);
    }

    private void loadTableData(){
        ObservableList<IntakeTm> programTmObservable=FXCollections.observableArrayList();
        for(Intake i:Database.intakeTable){
            Button removeButton=new Button("Delete");
            IntakeTm tm=new IntakeTm(
                    i.getProgramId(),
                    new SimpleDateFormat("yyyy-MM-dd").format(i.getStartDate()),
                    i.getIntakeName(),
                    i.getIntake(),
                    removeButton
            );
            programTmObservable.add(tm);
        }
        tblIntake.setItems(programTmObservable);
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}

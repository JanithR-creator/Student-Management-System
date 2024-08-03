package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ProgramFormController {
    public ComboBox<?> cmbTeacher;

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

    public TableView<?> tblTechnologies;

    public TextField txtCost;

    public TextField txtId;

    public TextField txtName;

    public TextField txtSearch;

    public TextField txtTechnology;

    public void backHomeOnAction(ActionEvent event) throws IOException {
        setUi("DashboardForm");

    }

    public void newProgramOnAction(ActionEvent event) {
        
    }

    public void saveOnAction(ActionEvent event) {

    }

    private void setUi(String location) throws IOException {
        Stage stage =(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}

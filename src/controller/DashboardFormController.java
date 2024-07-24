package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardFormController {
    public Label lblDate;
    public AnchorPane context;
    public Label lblTime;

    public void initialize(){
        setData();
    }

    private void setData() {
        /*Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String textDate = dateFormat.format(date);
        lblDate.setText(textDate);*/

        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // uda code ekama use kala
    }

    public void logOutOnAction(ActionEvent actionEvent) {
    }
}

package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardFormController {
    public Label lblDate;
    public AnchorPane context;
    public Label lblTime;

    public void initialize() {
        setData();
    }

    private void setData() {
        /*Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String textDate = dateFormat.format(date);
        lblDate.setText(textDate);*/

        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // uda code ekama use kala
        /* lblTime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));*/
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        e -> {
                            DateTimeFormatter dateTimeFormatter =
                                    DateTimeFormatter.ofPattern("hh:mm:ss");
                            lblTime.setText(LocalTime.now().format(dateTimeFormatter));
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    public void programOnAction(ActionEvent actionEvent) {
    }

    public void intakeOnAction(ActionEvent actionEvent) {
    }

    public void teacherOnAction(ActionEvent actionEvent) throws IOException {
        setUi("TeacherForm");
    }

    public void registrationOnAction(ActionEvent actionEvent) {
    }

    public void studentFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("StudentForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

}

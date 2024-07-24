package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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

    public void logOutOnAction(ActionEvent actionEvent) {
    }
}

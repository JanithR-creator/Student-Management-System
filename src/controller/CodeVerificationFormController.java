package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CodeVerificationFormController {
    public AnchorPane context;
    public TextField txtCode;
    public Label txtSelectedEmail;

    public void setUserData(int verificationCode, String email) {
        System.out.println(verificationCode);
        System.out.println(email);
    }

    public void verifyCodeOnAction(ActionEvent actionEvent) {
    }

    public void changeEmailOnAction(ActionEvent actionEvent) {
    }
}

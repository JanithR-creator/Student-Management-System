package controller;

import db.Database;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import util.security.PasswordManager;

import java.io.IOException;
import java.sql.*;

public class SignupFormController {
    public TextField txtFirstName;
    public TextField txtEmail;
    public AnchorPane context;
    public TextField txtLastName;
    public TextField txtPassword;

    public void alreadyHaveAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText().toLowerCase();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String password = new PasswordManager().encrypt(txtPassword.getText().trim());
       /* Database.userTable.add(
                new User(firstName, lastName, email, password)
        );
        new Alert(Alert.AlertType.CONFIRMATION, "Sign up successful").show();*/
        User createUser = new User(firstName, lastName, email, password);
        try {
            boolean isSaved = signup(createUser);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Welcome!").show();
                setUi("LoginForm");
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }
        } catch (SQLException e1) {
            new Alert(Alert.AlertType.ERROR, e1.toString()).show();
            System.out.println(e1);
        } catch (ClassNotFoundException e2) {
            new Alert(Alert.AlertType.ERROR, e2.toString()).show();
        }
    }

    /*private boolean signup(String email, String firstName, String lastName, String password){

    }*/

    private boolean signup(User user) throws ClassNotFoundException, SQLException {
       /* Class.forName("com.mysql.cj.jdbc.Driver");//load driver
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "1234");//create connection*/
        Connection connection = DbConnection.getInstance().getConnection();
        //String sql = "INSERT INTO user VALUES ('" + user.getEmail() + "','" + user.getFirstName() + "','" + user.getLastName() + "','" + user.getPassword() + "')";//write sql
        String sql = "INSERT INTO user VALUES (?,?,?,?)";//write sql
        //Statement statement = connection.createStatement();//create statement
        PreparedStatement statement = connection.prepareStatement(sql);//create statement
        statement.setString(1,user.getEmail());
        statement.setString(2,user.getFirstName());
        statement.setString(3,user.getLastName());
        statement.setString(4,user.getPassword());
        int rowCount = statement.executeUpdate(); //set sql into the statement and execute

        return rowCount > 0;//return boolean
    }
}

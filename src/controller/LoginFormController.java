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
import java.util.Optional;

public class LoginFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtPassword;

    public void forgotPasswordOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ForgotPasswordForm");
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText().toLowerCase();
        String password = txtPassword.getText().trim();

        try {
            User selectedUser = login(email);
            if(null!=selectedUser){
                if (new PasswordManager().checkPassword(password, selectedUser.getPassword())) {
                    setUi("DashboardForm");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Wrong Password").show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, String.format("%s not found", email)).show();
            }
        }catch (SQLException e1) {
            new Alert(Alert.AlertType.ERROR, e1.toString()).show();
        } catch (ClassNotFoundException e2) {
            new Alert(Alert.AlertType.ERROR, e2.toString()).show();
        }

       /* Optional<User> selectedUser = Database.userTable.stream().filter(e -> e.getEmail().equals(email)).findFirst();
        if (selectedUser.isPresent()) {
            if (new PasswordManager().checkPassword(password, selectedUser.get().getPassword())) {
                setUi("DashboardForm");
            } else {
                new Alert(Alert.AlertType.ERROR, "Wrong Password").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, String.format("%s not found", email)).show();
        }*/

    }

    public void createAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignupForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    private User login(String email) throws ClassNotFoundException, SQLException {
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "1234");*/
        Connection connection = DbConnection.getInstance().getConnection();
        //String sql = "SELECT * FROM user WHERE email='" + email + "'";
        String sql = "SELECT * FROM user WHERE email=?";
        //Statement statement = connection.createStatement();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,email);
        //ResultSet resultSet = statement.executeQuery(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {//record thiyeynm true otherwise false
            User user = new User(
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString(4)
            );
            System.out.println(user);
            return user;
        }
        return null;
    }
}

package controller;

import db.Database;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Student;
import view.tm.StudentTm;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class StudentFormController {
    public AnchorPane context;
    public TextField txtId;
    public TextField txtName;
    public DatePicker txtDOB;
    public TextField txtAddress;
    public TextField txtSearch;
    public TableView<StudentTm> tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDOB;
    public TableColumn colAddress;
    public TableColumn colOption;
    public Button btn;
    String searchText = "";

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        setStudentId();
        setTableData(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            setTableData(searchText);
        });

        tblStudent.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (null != newValue) {
                        setData(newValue);
                    }
                });
    }

    private void setData(StudentTm tm) {
        txtId.setText(tm.getId());
        txtName.setText(tm.getFullName());
        txtAddress.setText(tm.getAddress());
        txtDOB.setValue(LocalDate.parse(tm.getDob(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        btn.setText("Update Student");
    }

    private void setTableData(String searchText) {
        ObservableList<StudentTm> obList = FXCollections.observableArrayList();
        try {
            for (Student st : searchStudents(searchText)) {
                Button btn = new Button("Delete");
                StudentTm tm = new StudentTm(
                        st.getStudentId(),
                        st.getFullNAme(),
                        new SimpleDateFormat("yyyy-MM-dd").format(st.getDateOfBirth()),
                        st.getAddress(),
                        btn
                );
                btn.setOnAction(e -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Are you sure?",
                            ButtonType.YES,
                            ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {
                        try {
                            if (deleteStudent(st.getStudentId())) {
                                new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
                                setTableData(searchText);
                                setStudentId();
                            } else {
                                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                            }
                        } catch (ClassNotFoundException | SQLException ex) {
                            new Alert(Alert.AlertType.ERROR, e.toString()).show();
                        }
                        /*Database.studentTable.remove(st);
                        new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
                        setTableData(searchText);
                        setStudentId();*/
                    }
                });
                obList.add(tm);
            }
            tblStudent.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
        /*for (Student st : Database.studentTable) {
            if (st.getFullNAme().contains(searchText)) {
                Button btn = new Button("Delete");
                StudentTm tm = new StudentTm(
                        st.getStudentId(),
                        st.getFullNAme(),
                        new SimpleDateFormat("yyyy-MM-dd").format(st.getDateOfBirth()),
                        st.getAddress(),
                        btn
                );
                btn.setOnAction(e -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Are you sure?",
                            ButtonType.YES,
                            ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {
                        Database.studentTable.remove(st);
                        new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
                        setTableData(searchText);
                        setStudentId();
                    }
                });
                obList.add(tm);
            }
        }
        tblStudent.setItems(obList);
    }*/

    private void setStudentId() {

        try {
            String lastId = getLastId();
            if (null != null) {
                String splitData[] = lastId.split("-");
                String lastIdIntegerNumberAsAString = splitData[1];
                int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
                lastIntegerIdAsInt++;
                String generatedStudentId = "S-" + lastIntegerIdAsInt;
                txtId.setText(generatedStudentId);
            } else {
                txtId.setText("S-1");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*if (!Database.studentTable.isEmpty()) {
            Student lastStudent = Database.studentTable.get(
                    Database.studentTable.size() - 1
            );
            String lastId = lastStudent.getStudentId();
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsAString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            lastIntegerIdAsInt++;
            String generatedStudentId = "S-" + lastIntegerIdAsInt;
            txtId.setText(generatedStudentId);
        } else {
            txtId.setText("S-1");
        }*/
    }

    public void saveOnAction(ActionEvent actionEvent) {
        Student student = new Student(
                txtId.getText(),
                txtName.getText(),
                Date.from(txtDOB.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                txtAddress.getText()
        );

        if (btn.getText().equalsIgnoreCase("Save Student")) {

            try {
                if (saveStudent(student)) {
                    Database.studentTable.add(student);
                    setStudentId();
                    clear();
                    setTableData(searchText);
                    new Alert(Alert.AlertType.INFORMATION, "Student Saved!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.toString()).show();
            }

            /*Database.studentTable.add(student);
            setStudentId();
            clear();
            setTableData(searchText);
            new Alert(Alert.AlertType.INFORMATION, "Student Saved!").show();*/
        } else {

            try {
                if (updateStudent(student)) {
                    clear();
                    setTableData(searchText);
                    new Alert(Alert.AlertType.INFORMATION, "Student Updated!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

           /* for (Student st : Database.studentTable) {
                if (st.getStudentId().equals(txtId.getText())) {
                    st.setAddress(txtAddress.getText());
                    st.setDateOfBirth(Date.from(txtDOB.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    st.setFullNAme(txtName.getText());
                    setTableData(searchText);
                    clear();
                    setStudentId();
                    btn.setText("Save Student");
                    return;
                }
            }
            new Alert(Alert.AlertType.WARNING, "Not Found").show();*/
        }
    }

    private void clear() {
        txtDOB.setValue(null);
        txtName.clear();
        txtAddress.clear();
    }

    public void backHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void newStudentOnAction(ActionEvent actionEvent) {
        clear();
        setStudentId();
        btn.setText("Save Student");
    }

    public void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    private boolean saveStudent(Student student) throws ClassNotFoundException, SQLException {
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "1234");*/
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO student VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, student.getStudentId());
        statement.setString(2, student.getFullNAme());
        statement.setObject(3, student.getDateOfBirth());
        statement.setString(4, student.getAddress());

        int rowCount = statement.executeUpdate();

        return rowCount > 0;
    }

    private boolean updateStudent(Student student) throws ClassNotFoundException, SQLException {
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "1234");*/
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE student SET full_name=?,dob=?,address=? WHERE student_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, student.getFullNAme());
        statement.setObject(2, student.getDateOfBirth());
        statement.setString(3, student.getAddress());
        statement.setString(4, student.getStudentId());

        int rowCount = statement.executeUpdate();

        return rowCount > 0;
    }

    private String getLastId() throws ClassNotFoundException, SQLException {
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "1234");*/
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT student_id FROM student ORDER BY CAST(SUBSTRING(student_id,3) AS UNSIGNED) DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    private List<Student> searchStudents(String text) throws ClassNotFoundException, SQLException {
        text = "%" + text + "%";
       /* Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "1234");*/
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM student WHERE full_name LIKE ? OR address LIKE ?");
        statement.setString(1, text);
        statement.setString(2, text);
        ResultSet resultSet = statement.executeQuery();
        List<Student> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new Student(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getDate(3),
                            resultSet.getString(4)
                    )
            );
        }
        return list;
    }

    private boolean deleteStudent(String id) throws ClassNotFoundException, SQLException {
        /*Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "1234");*/
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM student WHERE student_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        return statement.executeUpdate() > 0;
    }
}

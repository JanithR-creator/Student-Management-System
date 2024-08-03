package db;

import model.Program;
import model.Student;
import model.Teacher;
import model.User;
import util.security.PasswordManager;

import java.util.ArrayList;

public class Database {
    public static ArrayList<User> userTable = new ArrayList<User>();
    public static ArrayList<Student> studentTable = new ArrayList<Student>();
    public static ArrayList<Teacher> teacherTable = new ArrayList<Teacher>();
    public static ArrayList<Program> programTable = new ArrayList<Program>();

    static {
        userTable.add(
                new User("Janith", "Ranasinghe", "janith@gmail.com",
                        new PasswordManager().encrypt("123"))
        );
    }
}

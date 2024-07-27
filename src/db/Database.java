package db;

import model.Student;
import model.User;
import util.security.PasswordManager;

import java.util.ArrayList;

public class Database {
    public static ArrayList<User> userTable = new ArrayList<User>();
    public static ArrayList<Student> studentTable = new ArrayList<Student>();

    static {
        userTable.add(
                new User("Janith", "Ranasinghe", "janith@gmail.com",
                        new PasswordManager().encrypt("123"))
        );
    }
}

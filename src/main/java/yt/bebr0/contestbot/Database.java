package yt.bebr0.contestbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * File is part of BeBrAPI. Thank you for using it! Also check out my YouTube channel where you can also leave your suggestions! https://www.youtube.com/c/BeBr0
 *
 * @author BeBr0
 */
public class Database {

    public static final Database instance = new Database();

    private Connection connection;

    private Database() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
        }
        catch (SQLException ignored) {}
    }
}

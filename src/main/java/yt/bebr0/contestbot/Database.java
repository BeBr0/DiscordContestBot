package yt.bebr0.contestbot;

import java.sql.*;

/**
 * File is part of BeBrAPI. Thank you for using it! Also check out my YouTube channel where you can also leave your suggestions! https://www.youtube.com/c/BeBr0
 *
 * @author BeBr0
 */
public class Database {

    public static final Database instance = new Database();

    private Statement statement;

    private Database() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:bot_db.sqlite");

            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS Admins(discord_id TEXT PRIMARY KEY)");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isAdmin(String discordId) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Admins WHERE discord_id = " + discordId);

            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAdmin(String id) {
        try {
            statement.execute("INSERT INTO Admins VALUES (" + id + ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

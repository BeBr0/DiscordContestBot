package yt.bebr0.contestbot;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.bebr0.contestbot.testing.task.Task;
import yt.bebr0.contestbot.testing.task.TestCase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
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

    public void addTask(@NotNull Task task) {
        try {
            statement.execute("INSERT INTO Tasks VALUES('" + task.getName() + "', '" + task.getTask() + "')");
        }
        catch (SQLException ignored) {}
    }

    public void addTestToDatabase(TestCase testCase) {
        try {
            statement.execute("INSERT INTO Tests(task_name, input, output) VALUES('" + testCase.getTask().getName() + "', '" + testCase.getInput() + "', '" + testCase.getExpectedOutput() + "')");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public Task getTask(String name) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Tasks WHERE task_name='" + name + "'");

            if (resultSet.next()) {
                return new Task(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void removeTask(String name) {
        try {
            statement.execute("DELETE FROM Tasks WHERE task_name=name");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> getTasks() {
        List<Task> result = new ArrayList<>();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Tasks");

            while (resultSet.next()) {
                result.add(new Task(resultSet.getString(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<TestCase> getTestCases(Task task) {
        List<TestCase> result = new ArrayList<>();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Tests WHERE task_name='" + task.getName() + "'");

            while (resultSet.next()) {
                result.add(new TestCase(task, resultSet.getString(3), resultSet.getString(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}

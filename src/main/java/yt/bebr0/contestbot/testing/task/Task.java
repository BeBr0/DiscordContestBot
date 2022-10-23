package yt.bebr0.contestbot.testing.task;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private static final List<Task> tasks = new ArrayList<>();

    @Nullable
    public static Task getTask(String name) {
        for (Task task : tasks) {
            if (task.name.equals(name))
                return task;
        }

        return null;
    }

    public static void addTask(Task task) {
        tasks.add(task);
    }

    public static void removeTask(Task task) {
        tasks.remove(task);
    }

    private final String name;
    private final String task;
    private final List<TestCase> testCases = new ArrayList<>();

    public Task(String name, String task) {
        this.name = name;
        this.task = task;
    }

    public void addTestCase(TestCase testCase) {
        testCases.add(testCase);
    }

    public String getName() {
        return name;
    }

    public String getTask() {
        return task;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }
}

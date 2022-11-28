package yt.bebr0.contestbot.testing.task;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private final String name;
    private final String task;
    private final int maxTimeMills;
    private final List<TestCase> testCases = new ArrayList<>();

    public Task(String name, String task, int maxTimeMills) {
        this.name = name;
        this.task = task;
        this.maxTimeMills = maxTimeMills;
    }

    public String getName() {
        return name;
    }

    public String getTask() {
        return task;
    }

    public void addTestCase(TestCase testCase) {
        testCases.add(testCase);
    }

    public int getMaxTimeMills() {
        return maxTimeMills;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }
}

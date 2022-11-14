package yt.bebr0.contestbot.testing.languages;

import yt.bebr0.contestbot.Database;
import yt.bebr0.contestbot.testing.task.Task;
import yt.bebr0.contestbot.testing.task.TestCase;

import java.util.ArrayList;
import java.util.List;

public abstract class Tester {

    protected abstract String run(String code, String input);

    public List<Boolean> test(Task task, String code) {
        List<Boolean> result = new ArrayList<>();

        for (TestCase testCase : Database.instance.getTestCases(task)) {
            result.add(testCase.isPassed(run(code, testCase.getInput())));
        }

        return result;
    }
}

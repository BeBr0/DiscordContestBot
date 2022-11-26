package yt.bebr0.contestbot.testing.languages;

import yt.bebr0.contestbot.Database;
import yt.bebr0.contestbot.testing.task.Task;
import yt.bebr0.contestbot.testing.task.TestCase;

import java.util.ArrayList;
import java.util.List;

public abstract class Tester {

    protected abstract String run(String code, String input);

    public Map<> test(Task task, String code) {
        List<Boolean> result = new ArrayList<>();

        for (TestCase testCase : Database.instance.getTestCases(task)) {
            String res = run(code, testCase.input());

            System.out.println(res);
            result.add(testCase.isPassed(res));
        }

        return result;
    }
}

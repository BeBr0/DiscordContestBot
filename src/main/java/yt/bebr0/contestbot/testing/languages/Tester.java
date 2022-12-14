package yt.bebr0.contestbot.testing.languages;

import yt.bebr0.contestbot.Database;
import yt.bebr0.contestbot.testing.TestResult;
import yt.bebr0.contestbot.testing.task.Task;
import yt.bebr0.contestbot.testing.task.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Tester {

    public static String noOutText = "*EMPTY OUTPUT*";

    protected abstract String run(String code, String input);

    public List<TestResult> test(Task task, String code) {
        List<TestResult> result = new ArrayList<>();

        for (TestCase testCase : Database.instance.getTestCases(task)) {
            String res = run(code, testCase.input());

            result.add(new TestResult(testCase.isPassed(res), res));

            if (!result.get(result.size() - 1).answer())
                break;
        }

        return result;
    }
}

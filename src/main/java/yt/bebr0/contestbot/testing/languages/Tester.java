package yt.bebr0.contestbot.testing.languages;

import yt.bebr0.contestbot.Database;
import yt.bebr0.contestbot.testing.TestResult;
import yt.bebr0.contestbot.testing.task.Task;
import yt.bebr0.contestbot.testing.task.TestCase;

import java.util.ArrayList;
import java.util.List;

public abstract class Tester {

    protected abstract String run(String code, String input);

    public List<TestResult> test(Task task, String code) {
        List<TestResult> result = new ArrayList<>();

        for (TestCase testCase : Database.instance.getTestCases(task)) {
            int[] time = new int[1];
            new Thread(() -> {
                if (time[0] == -1) {
                    Thread.yield();
                }
                time[0]++;
            }).start();

            String res = run(code, testCase.input());

            result.add(new TestResult(testCase.isPassed(res), time[0]));
        }

        return result;
    }
}

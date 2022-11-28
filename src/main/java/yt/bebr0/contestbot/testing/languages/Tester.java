package yt.bebr0.contestbot.testing.languages;

import yt.bebr0.contestbot.Database;
import yt.bebr0.contestbot.bot.Bot;
import yt.bebr0.contestbot.testing.TestResult;
import yt.bebr0.contestbot.testing.task.Task;
import yt.bebr0.contestbot.testing.task.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Tester {

    protected abstract String run(String code, String input);

    public List<TestResult> test(Task task, String code) {
        List<TestResult> result = new ArrayList<>();

        for (TestCase testCase : Database.instance.getTestCases(task)) {
            AtomicReference<Integer> time = new AtomicReference<>();
            time.set(0);

            Thread timeThread = new Thread(() -> {
                while (true) {
                    if (time.get() > task.getMaxTimeMills()) {
                        break;
                    }

                    if (time.get() == -1) {
                        break;
                    }

                    time.set(time.get() + 1);

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ignored) {}
                }
            });

            timeThread.start();
            String res = run(code, testCase.input());
            timeThread.interrupt();

            if (time.get() > task.getMaxTimeMills()) {
                result.add(new TestResult(false, time.get()));
            }
            else {
                result.add(new TestResult(testCase.isPassed(res), time.get()));
            }

            if (!result.get(result.size() - 1).answer())
                break;
        }

        return result;
    }
}

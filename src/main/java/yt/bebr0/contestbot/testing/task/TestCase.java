package yt.bebr0.contestbot.testing.task;

public class TestCase {

    private final String input;
    private final String expectedOutput;
    private final Task task;

    public TestCase(Task task, String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.task = task;
    }

    public boolean isPassed(String output) {
        return expectedOutput.equals(output);
    }

    public String getInput() {
        return input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public Task getTask() {
        return task;
    }
}

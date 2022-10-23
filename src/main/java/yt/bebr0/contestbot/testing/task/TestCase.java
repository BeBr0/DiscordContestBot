package yt.bebr0.contestbot.testing.task;

public class TestCase {

    private final String input;
    private final String expectedOutput;

    public TestCase(String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    public boolean isPassed(String output) {
        return expectedOutput.equals(output);
    }

    public String getInput() {
        return input;
    }

}

package yt.bebr0.contestbot.testing.task;

import yt.bebr0.contestbot.bot.Bot;

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
        if (output.endsWith("\n")) {
            output = output.substring(0, output.length() - 1).strip();
        }

        Bot.instance.textTo("817715041846558751", expectedOutput + " " + output);

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

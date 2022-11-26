package yt.bebr0.contestbot.testing.task;

public record TestCase(Task task, String input, String expectedOutput) {

    public boolean isPassed(String output) {
        if (output.endsWith("\n")) {
            output = output.substring(0, output.length() - 1).strip();
        }

        return expectedOutput.equals(output);
    }
}

package yt.bebr0.contestbot.testing.languages.impls;

import yt.bebr0.contestbot.testing.TestResult;
import yt.bebr0.contestbot.testing.languages.Tester;
import yt.bebr0.contestbot.testing.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class PythonTester extends Tester {

    public static final PythonTester instance = new PythonTester();

    @Override
    public List<TestResult> test(Task task, String code) {
        return super.test(task, code);
    }

    @Override
    protected String run(String code, String input) {
        File pyFile = new File("run.py");
        File output = new File("output.txt");
        File inputFile = new File("input.txt");

        try {
            pyFile.createNewFile();
            output.createNewFile();
            inputFile.createNewFile();

            FileWriter fileWriter = new FileWriter(pyFile);

            fileWriter.write(code);

            fileWriter.close();

            fileWriter = new FileWriter(inputFile);
            fileWriter.write(input);

            fileWriter.close();

            ProcessBuilder processBuilder = new ProcessBuilder("python3", pyFile.getAbsolutePath());

            processBuilder.redirectInput(inputFile);
            processBuilder.redirectOutput(output);

            Process process = processBuilder.start();

            Scanner scanner = new Scanner(output);
            StringBuilder outputString = new StringBuilder();

            process.waitFor();
            while (scanner.hasNextLine()) {
                outputString.append(scanner.nextLine());
            }

            return outputString.toString();
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return noOutText;
    }
}

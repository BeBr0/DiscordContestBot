package yt.bebr0.contestbot.testing.languages.impls;

import yt.bebr0.contestbot.testing.languages.Tester;
import yt.bebr0.contestbot.testing.task.Task;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class KotlinTester extends Tester {

    public static final KotlinTester instance = new KotlinTester();

    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    @Override
    public List<Boolean> test(Task task, String code) {
        return super.test(task, code);
    }

    @Override
    protected String run(String code, String input) {

        try {
            File fileToCompile = new File("Run.kt");

            fileToCompile.createNewFile();

            FileWriter fileWriter = new FileWriter(fileToCompile);
            fileWriter.append(code);

            fileWriter.close();

            File output = new File("output.txt");
            File inputFile = new File("input.txt");

            output.createNewFile();
            inputFile.createNewFile();

            fileWriter = new FileWriter(inputFile);
            fileWriter.write(input);

            fileWriter.close();

            ProcessBuilder processBuilder = new ProcessBuilder("kotlin", fileToCompile.getAbsolutePath());
            processBuilder.redirectErrorStream(true);
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "NO OUT";
    }
}

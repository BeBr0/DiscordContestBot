package yt.bebr0.contestbot.testing.languages.impls;

import yt.bebr0.contestbot.testing.TestResult;
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

/**
 * Code written by BeBr0. Check out my YouTube - <a href="https://www.youtube.com/c/BeBr0">...</a>
 *
 * @author BeBr0
 */

public class JavaTester extends Tester {

    public static final JavaTester instance = new JavaTester();

    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    @Override
    public List<TestResult> test(Task task, String code) {
        return super.test(task, code);
    }

    @Override
    protected String run(String code, String input) {
        String imports = "";

        if (code.contains("import") && code.contains("public")) {
            imports += code.substring(code.indexOf("import"), code.indexOf("public"));
        }

        String pureCode = code.substring(code.indexOf("{") + 1);

        String newCode = imports + "\npublic class Run {\n" + pureCode;

        try {
            File fileToCompile = new File("Run.java");

            fileToCompile.createNewFile();

            FileWriter fileWriter = new FileWriter(fileToCompile);
            fileWriter.append(newCode);

            fileWriter.close();

            File output = new File("output.txt");
            File inputFile = new File("input.txt");

            try {
                output.createNewFile();
                inputFile.createNewFile();

                fileWriter.close();

                fileWriter = new FileWriter(inputFile);
                fileWriter.write(input);

                fileWriter.close();

                ProcessBuilder processBuilder = new ProcessBuilder("java", fileToCompile.getAbsolutePath());
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
            }
            catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "NO OUT";
    }
}

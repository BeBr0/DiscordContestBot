package yt.bebr0.contestbot.testing.languages.impls;

import yt.bebr0.contestbot.testing.languages.Tester;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CppTester extends Tester {

    public static final CppTester instance = new CppTester();

    @Override
    protected String run(String code, String input) {

        try {
            File fileToCompile = new File("Run.cpp");

            fileToCompile.createNewFile();

            FileWriter fileWriter = new FileWriter(fileToCompile);
            fileWriter.append(code);

            fileWriter.close();

            File output = new File("output.txt");
            File inputFile = new File("input.txt");

            output.createNewFile();
            inputFile.createNewFile();

            ProcessBuilder processBuilder = new ProcessBuilder("g++", " -o", "program", fileToCompile.getAbsolutePath());

            Process process = processBuilder.start();
            process.waitFor();

            fileWriter = new FileWriter(inputFile);
            fileWriter.write(input);

            fileWriter.close();

            processBuilder = new ProcessBuilder("program.exe");

            processBuilder.redirectInput(inputFile);
            processBuilder.redirectOutput(output);

            process = processBuilder.start();

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

        return noOutText;
    }
}

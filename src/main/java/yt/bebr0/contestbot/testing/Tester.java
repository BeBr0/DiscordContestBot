package yt.bebr0.contestbot.testing;

import yt.bebr0.contestbot.bot.Bot;

import javax.tools.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Code written by BeBr0. Check out my YouTube - https://www.youtube.com/c/BeBr0
 *
 * @author BeBr0
 */

public class Tester {

    public static final Tester instance = new Tester();

    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public void runJava(String userId, String code) {

        code = "public class Run {\n public static void main(String[] args) {" + code + "}}";

        File fileToCompile = new File("Run.java");

        try {
            fileToCompile.createNewFile();

            FileWriter fileWriter = new FileWriter(fileToCompile);
            fileWriter.append(code);

            fileWriter.close();

            OutputStream outputStream = Files.newOutputStream(new File("out.txt").toPath());

            int result = compiler.run(null, outputStream, null, fileToCompile.getName());

            if (result == 0) {
                Bot.instance.textTo(userId, "Compile succeeded. Running test cases...");
            }
            else {
                Bot.instance.textTo(userId, "Compile stage failed");
            }

            Writer writer = new FileWriter(new File("out.txt"));
            DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
            List<String> classNames = Collections.singletonList("Run");

            JavaCompiler.CompilationTask compilationTask = compiler.getTask(
                    writer,
                    null,
                    diagnosticCollector,
                    null,
                    classNames,
                    filesToCompile
            );
        }
        catch (IOException ignored) {}
    }
}

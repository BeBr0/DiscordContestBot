package yt.bebr0.contestbot.testing;

import yt.bebr0.contestbot.bot.Bot;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Code written by BeBr0. Check out my YouTube - https://www.youtube.com/c/BeBr0
 *
 * @author BeBr0
 */

public class Tester {

    public static final Tester instance = new Tester();

    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public void run(String userId, String code) {
        File fileToCompile = new File("Run.java");

        try {
            FileWriter fileWriter = new FileWriter(fileToCompile);
            fileWriter.append(code);

            int result = compiler.run(null, null, null, fileToCompile.getName());

            Bot.instance.textToSystem(userId, "Code stopped with exit code " + code);
        }
        catch (IOException ignored) {}
    }
}

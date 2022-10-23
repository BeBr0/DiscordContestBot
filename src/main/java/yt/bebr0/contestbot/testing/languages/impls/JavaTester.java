package yt.bebr0.contestbot.testing.languages.impls;

import org.python.antlr.ast.Str;
import yt.bebr0.contestbot.testing.languages.Tester;
import yt.bebr0.contestbot.testing.task.Task;
import yt.bebr0.contestbot.testing.task.TestCase;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Code written by BeBr0. Check out my YouTube - <a href="https://www.youtube.com/c/BeBr0">...</a>
 *
 * @author BeBr0
 */

public class JavaTester extends Tester {

    public static final JavaTester instance = new JavaTester();

    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    @Override
    public List<Boolean> test(Task task, String code) {
        return super.test(task, code);
    }

    private void compile(File file) {
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
        Iterable<JavaFileObject> fileObjects = (Iterable<JavaFileObject>) manager.getJavaFileObjectsFromFiles(Arrays.asList(file));

        compiler.getTask(null, manager, null, null, null, fileObjects).call();
    }

    @Override
    protected String run(String code, String input) {

        String newCode = "package yt.bebr0.contestbot.testing;\n\npublic class Run {\n public static void main(String[] args) {\n" + code + "}\n}";

        try {
            File fileToCompile = new File("src/main/java/yt/bebr0/contestbot/testing", "Run.java");

            fileToCompile.createNewFile();

            FileWriter fileWriter = new FileWriter(fileToCompile);
            fileWriter.append(newCode);

            fileWriter.close();

            compile(fileToCompile);

            Class<?> clazz = Class.forName("yt.bebr0.contestbot.testing.Run");

            Method main = clazz.getDeclaredMethod("main", String[].class);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(out);
            InputStream inputStream = new ByteArrayInputStream(input.getBytes());
            System.setOut(ps);
            System.setIn(inputStream);
            main.invoke(main, new Object[]{new String[0]});

            fileToCompile.delete();

            return out.toString();
        }
        catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
               InvocationTargetException ignored) {}

        return "NO OUT";
    }
}

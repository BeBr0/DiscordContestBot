package yt.bebr0.contestbot.testing.languages.impls;

import yt.bebr0.contestbot.testing.languages.Tester;
import yt.bebr0.contestbot.testing.task.Task;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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

        String imports = code.substring(code.indexOf("import"), code.indexOf("public"));
        String pureCode = code.substring(code.indexOf("{") + 1);

        String newCode = "package yt.bebr0.contestbot.testing;\n" + imports + "\npublic class Run {\n" + pureCode;

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

            return out.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e){
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return "NO OUT";
    }
}

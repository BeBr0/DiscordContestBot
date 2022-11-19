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

        String newCode = imports + "\npublic class Run {\n" + pureCode;

        try {
            File fileToCompile = new File("Run.java");

            fileToCompile.createNewFile();

            FileWriter fileWriter = new FileWriter(fileToCompile);
            fileWriter.append(newCode);

            fileWriter.close();

            compile(fileToCompile);

            return PythonTester.instance.run(
                    "import os.path,subprocess\n" +
                            "from subprocess import STDOUT,PIPE\n" +
                            "\n" +
                            "def compile_java(java_file):\n" +
                            "    subprocess.check_call(['javac', java_file])\n" +
                            "\n" +
                            "def execute_java(java_file, stdin):\n" +
                            "    java_class,ext = os.path.splitext(java_file)\n" +
                            "    cmd = ['java', java_class]\n" +
                            "    proc = subprocess.Popen(cmd, stdin=PIPE, stdout=PIPE, stderr=STDOUT)\n" +
                            "    stdout,stderr = proc.communicate(stdin)\n" +
                            "    print (stdout)\n" +
                            "execute_java('" + fileToCompile.getName() + "', '" + input + "')",
                    input
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "NO OUT";
    }
}

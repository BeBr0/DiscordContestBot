package yt.bebr0.contestbot.testing.languages.impls;

import org.python.util.PythonInterpreter;
import yt.bebr0.contestbot.testing.languages.Tester;
import yt.bebr0.contestbot.testing.task.Task;
import yt.bebr0.contestbot.testing.task.TestCase;

import java.io.CharArrayReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public class PythonTester extends Tester {

    public static final PythonTester instance = new PythonTester();

    @Override
    public List<Boolean> test(Task task, String code) {
        return super.test(task, code);
    }

    @Override
    protected String run(String code, String input) {
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            StringWriter output = new StringWriter();
            Reader reader = new CharArrayReader(input.toCharArray());
            interpreter.setOut(output);
            interpreter.setIn(reader);

            interpreter.exec(code);

            return output.getBuffer().toString();
        }
    }
}

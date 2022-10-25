package yt.bebr0.contestbot.testing.languages;

import yt.bebr0.contestbot.testing.languages.impls.JavaTester;
import yt.bebr0.contestbot.testing.languages.impls.PythonTester;

public enum Language {

    JAVA("java", JavaTester.instance, "//"),
    PYTHON("python", PythonTester.instance, "#"),
    ;

    private final String name;
    private final Tester tester;
    private final String commentMarker;

    Language(String name, Tester tester, String commentMarker) {
        this.name = name;
        this.tester = tester;
        this.commentMarker = commentMarker;
    }

    public String getName() {
        return name;
    }

    public Tester getTester() {
        return tester;
    }

    public String getCommentMarker() {
        return commentMarker;
    }
}

package io.testomat.e2e_tests_light_ui.utils;

public class StringParsers {
    public static Integer parseIntegerFromString(String countOfTests) {
        String digitText = countOfTests.replaceAll("\\D+", "");
        return Integer.parseInt(digitText);
    }
}

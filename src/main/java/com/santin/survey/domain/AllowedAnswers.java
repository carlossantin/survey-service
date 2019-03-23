package com.santin.survey.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AllowedAnswers {

    private static Set<String> allowedAnswers = new HashSet<>(Arrays.asList("SIM", "NAO"));

    public static boolean isValidAnswer(String answer) {
        return allowedAnswers.contains(answer.trim().toUpperCase());
    }

    public static String getAllowedValues() {
        return allowedAnswers.stream()
                .collect(Collectors.joining(", "));
    }
}

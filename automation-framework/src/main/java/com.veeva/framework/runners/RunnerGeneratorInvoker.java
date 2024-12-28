package com.veeva.framework.runners;

public class RunnerGeneratorInvoker {

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Please provide the feature path and glue path as arguments.");
        }

        String featurePath = args[0];
        String gluePath = args[1];

        RunnerClassGenerator.generateRunners(featurePath, gluePath);
    }
}

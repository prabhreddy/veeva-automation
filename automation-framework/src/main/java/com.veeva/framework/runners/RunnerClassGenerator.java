package com.veeva.framework.runners;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RunnerClassGenerator {

    private static final String RUNNER_OUTPUT_DIR = "src/generated/java/com/veeva/coreproduct/runners/";

    public static void generateRunners(String featureDir, String gluePath) {
        File featureDirectory = new File(featureDir);
        File[] featureFiles = featureDirectory.listFiles((dir, name) -> name.endsWith(".feature"));

        if (featureFiles == null || featureFiles.length == 0) {
            throw new RuntimeException("No feature files found in " + featureDir);
        }

        for (File featureFile : featureFiles) {
            String className = featureFile.getName().replace(".feature", "Runner");
            String classContent = generateRunnerClassContent(className, featureFile.getPath(), gluePath);
            writeRunnerClass(className, classContent);
        }
    }

    private static String generateRunnerClassContent(String className, String featurePath, String gluePath) {
        return "package com.veeva.coreproduct.runners;\n\n" +
                "import io.cucumber.testng.AbstractTestNGCucumberTests;\n" +
                "import io.cucumber.testng.CucumberOptions;\n\n" +
                "@CucumberOptions(\n" +
                "    features = \"" + featurePath + "\",\n" +
                "    glue = \"" + gluePath + "\",\n" +
                "    plugin = {\"pretty\", \"html:target/cucumber-reports/" + className + ".html\"},\n" +
                "    monochrome = true\n" +
                ")\n" +
                "public class " + className + " extends AbstractTestNGCucumberTests {\n" +
                "}\n";
    }

    private static void writeRunnerClass(String className, String classContent) {
        try {
            File outputDir = new File(RUNNER_OUTPUT_DIR);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            File runnerFile = new File(outputDir, className + ".java");
            try (FileWriter writer = new FileWriter(runnerFile)) {
                writer.write(classContent);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write runner class for " + className, e);
        }
    }
}

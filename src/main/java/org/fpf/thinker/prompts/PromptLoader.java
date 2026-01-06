package org.fpf.thinker.prompts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PromptLoader {

    public static List<PromptSpecification> loadPrompts(String directoryPath) {
        if (directoryPath == null || directoryPath.isBlank()) {
            throw new IllegalArgumentException("FPF_PROMPTS_DIR environment variable is not set");
        }

        Path dir = Paths.get(directoryPath);
        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            throw new IllegalArgumentException("Prompts directory does not exist or is not a directory: " + directoryPath);
        }

        List<PromptSpecification> prompts = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(dir, 1)) {
            paths.filter(Files::isRegularFile)
                 .filter(p -> p.toString().endsWith(".md"))
                 .forEach(path -> {
                     try {
                         String content = Files.readString(path);
                         prompts.add(FileBasedPrompt.parse(content));
                     } catch (IOException e) {
                         throw new RuntimeException("Failed to load prompt from file: " + path, e);
                     }
                 });
        } catch (IOException e) {
            throw new RuntimeException("Failed to scan prompts directory: " + directoryPath, e);
        }

        return prompts;
    }
}

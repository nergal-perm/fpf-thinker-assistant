package org.fpf.thinker.prompts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.GetPromptRequest;
import io.modelcontextprotocol.spec.McpSchema.GetPromptResult;
import io.modelcontextprotocol.spec.McpSchema.Prompt;
import io.modelcontextprotocol.spec.McpSchema.PromptArgument;
import io.modelcontextprotocol.spec.McpSchema.PromptMessage;
import io.modelcontextprotocol.spec.McpSchema.Role;
import io.modelcontextprotocol.spec.McpSchema.TextContent;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Implementation of PromptSpecification that reads from a file with YAML frontmatter.
 */
public class FileBasedPrompt implements PromptSpecification {

    private final Prompt promptDefinition;
    private final String templateBody;

    // POJO for parsing YAML Frontmatter
    private static class Frontmatter {
        @JsonProperty("name")
        String name;
        @JsonProperty("description")
        String description;
        @JsonProperty("arguments")
        List<String> arguments;
    }

    private FileBasedPrompt(Prompt promptDefinition, String templateBody) {
        this.promptDefinition = promptDefinition;
        this.templateBody = templateBody;
    }

    /**
     * Factory method to create an instance from raw file content.
     * @param fileContent The complete content of the file (YAML + Markdown).
     * @return A valid FileBasedPrompt instance.
     * @throws IOException If parsing fails or content is invalid.
     */
    public static FileBasedPrompt parse(String fileContent) throws IOException {
        if (!fileContent.startsWith("---")) {
            throw new IOException("Invalid file format: Missing YAML frontmatter start delimiter");
        }

        int endDelimiterIndex = fileContent.indexOf("\n---", 3);
        if (endDelimiterIndex == -1) {
             // Try checking for windows style line ending
             endDelimiterIndex = fileContent.indexOf("\r\n---", 3);
             if (endDelimiterIndex == -1) {
                throw new IOException("Invalid file format: Missing YAML frontmatter end delimiter");
             }
        }

        String yamlContent = fileContent.substring(3, endDelimiterIndex);
        // +4 to skip "\n---" or +5 for "\r\n---". Let's handle generic newline + delimiter length
        int bodyStartIndex = endDelimiterIndex + (fileContent.startsWith("\r\n", endDelimiterIndex) ? 5 : 4);
        
        // Skip optional newline after the second delimiter
        if (bodyStartIndex < fileContent.length() && fileContent.charAt(bodyStartIndex) == '\n') {
            bodyStartIndex++;
        } else if (bodyStartIndex < fileContent.length() && fileContent.startsWith("\r\n", bodyStartIndex)) {
            bodyStartIndex += 2;
        }
        
        String markdownBody = (bodyStartIndex < fileContent.length()) ? fileContent.substring(bodyStartIndex) : "";

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Frontmatter frontmatter = mapper.readValue(yamlContent, Frontmatter.class);

        if (frontmatter.name == null || frontmatter.name.isBlank()) {
            throw new IOException("Invalid frontmatter: 'name' is required");
        }

        List<PromptArgument> args = (frontmatter.arguments == null) ? List.of() :
                frontmatter.arguments.stream()
                        .map(argName -> new PromptArgument(argName, null, true))
                        .toList();

        Prompt prompt = new Prompt(frontmatter.name, frontmatter.description, args);

        return new FileBasedPrompt(prompt, markdownBody);
    }

    @Override
    public Prompt getPromptDefinition() {
        return this.promptDefinition;
    }

    @Override
    public BiFunction<McpSyncServerExchange, GetPromptRequest, GetPromptResult> getHandler() {
        return (exchange, request) -> {
            String resultText = templateBody;
            Map<String, Object> args = request.arguments();

            if (promptDefinition.arguments() != null) {
                for (PromptArgument arg : promptDefinition.arguments()) {
                    String key = arg.name();
                    Object valObj = (args != null) ? args.get(key) : null;
                    String value = (valObj != null) ? valObj.toString() : null;

                    if (value == null) {
                         throw new IllegalArgumentException("Missing required argument: " + key);
                    }
                    
                    // Simple replacement: {{ key }}
                    // We handle optional spaces around the key: {{key}}, {{ key }}, {{  key  }}
                    resultText = resultText.replaceAll("\\{\\{\\s*" + key + "\\s*\\}\\}", value);
                }
            }

            PromptMessage message = new PromptMessage(Role.USER, new TextContent(resultText));
            return new GetPromptResult(null, List.of(message));
        };
    }
}

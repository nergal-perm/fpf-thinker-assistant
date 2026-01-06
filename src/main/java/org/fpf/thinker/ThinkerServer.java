package org.fpf.thinker;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.json.jackson.JacksonMcpJsonMapper;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;
import org.fpf.thinker.prompts.PromptLoader;
import org.fpf.thinker.prompts.PromptSpecification;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ThinkerServer {

    public static void main(String[] args) {
        System.err.println("FPF Thinker Assistant - Server Starting...");

        try {
            String promptsDir = System.getenv("FPF_PROMPTS_DIR");
            System.err.println("Loading prompts from: " + promptsDir);
            
            List<PromptSpecification> prompts = PromptLoader.loadPrompts(promptsDir);
            System.err.println("Loaded " + prompts.size() + " prompts.");

            var capabilitiesBuilder = McpSchema.ServerCapabilities.builder()
                    .tools(true)
                    .prompts(true);

            // Server instantiation
            var serverBuilder = McpServer.sync(new StdioServerTransportProvider(new JacksonMcpJsonMapper(new ObjectMapper())))
                    .serverInfo("fpf-thinker", "0.1.0")
                    .capabilities(capabilitiesBuilder.build());

            // Register Prompts
            serverBuilder.prompts(prompts.stream()
                    .map(PromptSpecification::asRegistration)
                    .toArray(McpServerFeatures.SyncPromptSpecification[]::new));

            serverBuilder.build();

            System.err.println("Server initialized. Listening on Stdio...");
        } catch (Exception e) {
            System.err.println("Critical Error: " + e.getMessage() +
                               "\nStack: " + Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n")));
            System.exit(1);
        }
    }
}

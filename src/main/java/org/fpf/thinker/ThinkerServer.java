package org.fpf.thinker;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.json.jackson.JacksonMcpJsonMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;

public class ThinkerServer {

    public static void main(String[] args) {
        System.err.println("FPF Thinker Assistant - Server Starting...");

        try {
            McpServer.sync(new StdioServerTransportProvider(new JacksonMcpJsonMapper(new ObjectMapper())))
                    .serverInfo("fpf-thinker", "0.1.0")
                    .capabilities(McpSchema.ServerCapabilities.builder()
                            .tools(true)
                            .prompts(true)
                            .build())
                    .build();
            System.err.println("Server initialized. Listening on Stdio...");
        } catch (Exception e) {
            System.err.println("Critical Error: " + e.getMessage());
            System.exit(1);
        }
    }
}

package org.fpf.thinker.prompts;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema.GetPromptResult;
import io.modelcontextprotocol.spec.McpSchema.GetPromptRequest;
import io.modelcontextprotocol.spec.McpSchema.Prompt;

import java.util.function.BiFunction;

/**
 * Interface defining the contract for an MCP Prompt.
 * Encapsulates both the static definition (Model) and the execution logic (Controller).
 */
public interface PromptSpecification {

    default McpServerFeatures.SyncPromptSpecification asRegistration() {
        return new McpServerFeatures.SyncPromptSpecification(
                this.getPromptDefinition(),
                this.getHandler()
        );
    }

    /**
     * Returns the static definition of the prompt.
     * @return The MCP Prompt object containing name, description, and arguments.
     */
    Prompt getPromptDefinition();

    /**
     * Returns the handler function responsible for executing the prompt.
     * @return A function that takes a GetPromptRequest and returns a GetPromptResult.
     */
    BiFunction<McpSyncServerExchange, GetPromptRequest, GetPromptResult> getHandler();
}

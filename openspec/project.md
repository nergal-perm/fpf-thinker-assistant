# Project Context

## Purpose
The **FPF Thinker Assistant** is a specialized Model Context Protocol (MCP) server designed to guide users (and AI agents) through rigorous First Principles reasoning. It acts as the "Laboratory" of the system, providing the *soft constraints* (heuristics, cognitive scaffolding, prompt engineering) required to convert vague "Problems" into structured "Hypotheses" and "Decisions".

## Tech Stack
- **Language:** Java (JDK 21+)
- **Build System:** Maven
- **Protocol:** Model Context Protocol (MCP) + `stdio` transport protocol

## Project Conventions

### Code Style
- **Conventions:** Follow standard Java naming and formatting conventions (e.g., Google Java Style).
- **Documentation:** Javadoc for public APIs and complex logic.
- **Structure:** Standard Maven directory layout (`src/main/java`, `src/test/java`).

### Architecture Patterns
- **Standalone MCP Server:** The application runs as an independent process exposing MCP capabilities (Prompts, Tools).
- **Prompts as Code:** Logic layer reads and parses Markdown prompt templates from the local filesystem rather than using hardcoded strings.

### Component Design
- **Specification Pattern:** derived from `fpf-governance-mcp`. Each MCP capability (Prompt, Tool) must be encapsulated in a dedicated class implementing a common interface (e.g., `PromptSpecification`).
    - **Contract:** The interface must expose the Model (metadata/schema) and the Controller (handler function) separately.
    - **Builder Support:** The interface should provide a default method to convert the implementation into the native SDK's builder format (e.g., `SyncPromptRegistration`).
    - **Registration:** Main server class aggregates these components rather than defining inline logic.

### Testing Strategy
- **Unit Testing:** JUnit 5 for core logic (Prompt parsing, Request handling).
- **Mocking:** No mocks! Utilize the 'Testing without mocks' approach as described by James Shore.
- **Goal:** Ensure the server correctly loads templates and formats responses before integration.

### Git Workflow
- Feature branches for all changes.
- Conventional Commits for commit messages.
- Pull Requests for merging into `main`.

## Domain Context
- **First Principles Framework (FPF):** The system implements specific cognitive workflows.
- **ADI Cycle:** Abductive (Guess) -> Deductive (Plan) -> Inductive (Act).
- **Concepts:**
    - **Governor:** The deterministic "Law" (External system).
    - **Thinker:** The probabilistic "Scientist" (This system).
    - **Problem:** The starting point of reasoning.

## Important Constraints
- **Dynamic Loading:** Prompts must be loaded from the Vault directory to allow for rapid evolution of "Thinking" without recompiling the server.

## External Dependencies
- **MCP Client:** Relies on an MCP-compliant client (e.g., Claude Desktop, IDE) to interact with the server.
- **File System:** Requires read access to some local directory containing Markdown prompt templates. The directory should be configurable via environment variable.
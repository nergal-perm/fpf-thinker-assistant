# Tasks: Prompts as Code

1.  Add `jackson-dataformat-yaml` dependency to `pom.xml`.
2.  Create `PromptSpecification` interface to define the contract for prompts (Model + Handler).
3.  Implement `FileBasedPrompt` class:
    -   POJO for YAML Frontmatter (Name, Description, Args).
    -   Logic to parse the file content (split Frontmatter/Body).
    -   Logic to substitute `{{ var }}` with values.
4.  Implement `PromptLoader` service:
    -   Read `FPF_PROMPTS_DIR`.
    -   Scan for `*.md` files.
    -   Instantiate `FileBasedPrompt` objects.
    -   Fail fast on IO or Parse errors.
5.  Wire `PromptLoader` into `ThinkerServer`:
    -   Register loaded prompts with `McpServer`.
6.  Verify with manual test:
    -   Create a dummy prompt file.
    -   Run server.
    -   Inspect MCP output.

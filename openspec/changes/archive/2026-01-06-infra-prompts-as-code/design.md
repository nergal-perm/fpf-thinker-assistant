# Design: Prompts as Code Infrastructure

## Context
The FPF Thinker Assistant needs to serve dynamic prompts defined in Markdown files rather than hardcoded strings. This allows for rapid iteration of "thinking" patterns without recompiling the Java server.

## Architectural Decisions

### 1. File Format
We adopt a "Frontmatter + Body" format, widely used in static site generators (Jekyll, Hugo).
- **Frontmatter (YAML):** Contains metadata required by the MCP protocol (`name`, `description`, `arguments`).
- **Body (Markdown):** Contains the actual prompt text with placeholders.

**Example:**
```markdown
---
name: "fpf_deconstruct"
description: "Deconstructs a problem into atomic facts"
arguments:
  - "problem_statement"
---
You are a First Principles thinker. Deconstruct this problem:
{{ problem_statement }}
```

### 2. Templating Engine
- **Decision:** Use a custom, lightweight string replacer instead of a full templating engine (Handlebars/Mustache).
- **Rationale:** The requirement is simple variable substitution. Adding a heavy dependency is unnecessary complexity for the MVP.
- **Syntax:** `{{ variable_name }}`.

### 3. Loading Strategy
- **Decision:** Fail-fast on startup.
- **Rationale:** If a prompt is malformed, the server's "thinking" capabilities are compromised. It is safer to crash and force a fix than to run in a degraded state where prompts might be missing.
- **Discovery:** Environment variable `FPF_PROMPTS_DIR`.

### 4. Class Structure (Specification Pattern)
Following the project's `Specification Pattern`:
- `FileBasedPrompt`: Implements `PromptSpecification`.
    - Responsible for holding the parsed data (name, description, args, template).
    - Implements the `handler()` to perform the substitution at runtime.
- `PromptLoader`: Service responsible for scanning the directory, parsing files (using Jackson YAML), and returning a list of `FileBasedPrompt` instances.

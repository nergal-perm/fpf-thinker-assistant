# Proposal: Infrastructure for Prompts as Code

## Goal
Establish the foundational infrastructure to load, parse, and serve MCP Prompts dynamically from a local directory of Markdown files.

## Scope
1.  **File System Scanner:** Read files from `FPF_PROMPTS_DIR`.
2.  **Parser:** Extract YAML frontmatter and Markdown body.
3.  **Prompt Engine:** Register these files as MCP Prompts and handle execution requests by substituting variables.
4.  **Dependencies:** Add `jackson-dataformat-yaml`.

## Success Criteria
- The server starts only if `FPF_PROMPTS_DIR` is valid.
- Prompts defined in the directory are listed in `mcp_list_prompts`.
- Calling `mcp_get_prompt` returns the text with variables substituted.
- Malformed files prevent server startup.

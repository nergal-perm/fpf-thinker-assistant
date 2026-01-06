# prompt-engine Specification

## Purpose
Establish the foundational infrastructure to load, parse, and serve MCP Prompts dynamically from a local directory of Markdown files, enabling "Prompts as Code" workflow.
## Requirements
### Requirement: Load Prompts from Filesystem
The system MUST load prompt definitions from a flat directory specified by the `FPF_PROMPTS_DIR` environment variable at startup.

#### Scenario: Successful Loading
Given the environment variable `FPF_PROMPTS_DIR` points to a valid directory
And the directory contains valid markdown files with frontmatter
When the server starts
Then it should expose those prompts via the MCP protocol

#### Scenario: Missing Directory
Given `FPF_PROMPTS_DIR` is not set or points to a non-existent path
When the server starts
Then it should terminate with a helpful error message

#### Scenario: Malformed File
Given the prompts directory contains a file with invalid YAML frontmatter
When the server starts
Then it should terminate immediately with a parsing error

### Requirement: Parse Prompt Format
The system MUST parse files consisting of a YAML frontmatter block (delimited by `---`) and a Markdown body.

#### Scenario: Field Mapping
Given a file content:
```markdown
---
name: "test_prompt"
description: "A test prompt"
arguments: ["arg1"]
---
Body {{ arg1 }}
```
When the file is parsed
Then the MCP Prompt object should have name="test_prompt", description="A test prompt"
And it should require one argument "arg1"

### Requirement: Execute Prompt with Substitution
The system MUST replace `{{ variable }}` placeholders in the Markdown body with values provided in the MCP request arguments.

#### Scenario: Variable Substitution
Given a prompt with body "Hello {{ name }}!"
When a client requests this prompt with arguments `{"name": "World"}`
Then the server should return a PromptMessage with content "Hello World!"

#### Scenario: Missing Argument
Given a prompt requires argument `topic`
When a client requests this prompt without providing `topic`
Then the server should return an error indicating the missing argument


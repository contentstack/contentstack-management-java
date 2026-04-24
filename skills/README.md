# Skills – Contentstack Management Java SDK

Source of truth for detailed guidance. Read [AGENTS.md](../AGENTS.md) first, then open the skill that matches your task.

## When to use which skill

| Skill folder | Use when |
| --- | --- |
| [dev-workflow](dev-workflow/SKILL.md) | Branching (`development` → `master`, back-merge, GitHub Release publish), Maven/CI, publish touchpoints. |
| [contentstack-java-cma-sdk](contentstack-java-cma-sdk/SKILL.md) | Changing public API, `Contentstack` / `Stack` flows, auth tokens, or SDK surface exposed to integrators. |
| [java](java/SKILL.md) | Package structure under `com.contentstack.cms`, Java 8 compatibility, Lombok, imports, and code style in this repo. |
| [testing](testing/SKILL.md) | Adding or fixing tests, Surefire `skipTests` behavior, MockWebServer or live API tests, env/credentials. |
| [code-review](code-review/SKILL.md) | Preparing or reviewing a PR: scope, tests, API compatibility, and security around tokens. |
| [http-client-stack](http-client-stack/SKILL.md) | Retrofit services, OkHttp client/interceptors, retries, proxies, or HTTP logging. |

Each folder contains `SKILL.md` with YAML frontmatter (`name`, `description`).

---
name: code-review
description: Use when reviewing or preparing a PR—API safety, tests, auth handling, and compatibility checklist.
---

# Code review – Contentstack Management Java SDK

## When to use

- You are reviewing a pull request or self-reviewing before request.
- You need a quick severity rubric (blocker / major / minor) aligned with this SDK.

## Instructions

### Blocker

- Real credentials, tokens, or stack keys committed in source or fixtures.
- Breaking public API changes without version strategy or without team agreement.
- New network behavior that bypasses existing interceptors, retry policy, or auth without explicit design.

### Major

- Missing or insufficient tests for new CMA behavior or error paths.
- Behavior change for `Contentstack` / `Stack` builders or defaults without README or Javadoc updates where integrators would notice.
- Regressions for Java 8 compatibility or new compiler warnings left unaddressed.

### Minor

- Style inconsistencies fixable in follow-up, or internal refactors with no API impact.
- Javadoc typos or non-user-facing comment cleanup.

### PR checklist (short)

- [ ] `mvn clean test -DskipTests=false` passes locally.
- [ ] Public changes documented (Javadoc / README / changelog per team process).
- [ ] No secrets in repo; test config uses env or mocks.
- [ ] HTTP changes reviewed against [http-client-stack/SKILL.md](../http-client-stack/SKILL.md).

## References

- [dev-workflow/SKILL.md](../dev-workflow/SKILL.md)
- [contentstack-java-cma-sdk/SKILL.md](../contentstack-java-cma-sdk/SKILL.md)

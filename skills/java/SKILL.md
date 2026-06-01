---
name: java
description: Use for Java package layout, Java 8 constraints, Lombok, and coding conventions in this repository.
---

# Java – Contentstack Management Java SDK

## When to use

- You add new types under `com.contentstack.cms` or reorganize packages.
- You need to stay compatible with Java 8 (`maven.compiler.source` / `target` = 1.8).
- You use or configure Lombok and need to match existing patterns.

## Instructions

### Layout

- **Main code:** `src/main/java/com/contentstack/cms/` — core client in `Contentstack`, stack features under `stack/`, HTTP helpers in `core/`, OAuth under `oauth/`, models under `models/`.
- **Tests:** mirror packages under `src/test/java/com/contentstack/cms/`; Surefire includes `**/Test*.java`, `**/*Test.java`, `**/*Tests.java`, and related patterns per [`pom.xml`](../../pom.xml).

### Language level

- Do not use language or library features that require a JVM newer than 8 for **source** compatibility unless the project explicitly upgrades (check `maven-compiler-plugin` and `<maven.compiler.source>` in [`pom.xml`](../../pom.xml)).

### Lombok and tooling

- Lombok is on the annotation processor path; prefer the same style as neighboring classes (`@Getter`, `@Builder`, etc.) instead of mixing hand-written boilerplate inconsistently.
- JetBrains `@NotNull` / nullable annotations appear in places like [`Contentstack`](../../src/main/java/com/contentstack/cms/Contentstack.java); follow existing nullability hints for new APIs.

### Compiler

- The compiler enables broad `-Xlint` checks; fix new warnings rather than suppressing without cause.

## References

- [contentstack-java-cma-sdk/SKILL.md](../contentstack-java-cma-sdk/SKILL.md)
- [testing/SKILL.md](../testing/SKILL.md)

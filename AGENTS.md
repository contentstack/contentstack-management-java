# Contentstack Management Java SDK – Agent guide

*Universal entry point* for contributors and AI agents. Detailed conventions live in **skills/*/SKILL.md**.

## What this repo is

| Field | Detail |
| --- | --- |
| *Name:* | [contentstack-management-java](https://github.com/contentstack/contentstack-management-java) |
| *Purpose:* | Java client library for the Contentstack Content Management API (CMA): create, update, delete, and fetch account content and configuration from JVM applications. |
| *Out of scope (if any):* | Content Delivery API (CDA) and delivery-only use cases belong in the Delivery SDK, not this package. This SDK is read/write against CMA; do not treat it as the primary way to serve content to end users. |

## Tech stack (at a glance)

| Area | Details |
| --- | --- |
| Language | Java 8 (source/target in `pom.xml`); README suggests Java 8+ for consumers. |
| Build | Apache Maven; `pom.xml` at repo root. |
| Tests | JUnit 5 / Vintage, Mockito, OkHttp MockWebServer; tests under `src/test/java/com/contentstack/cms/`. |
| Lint / coverage | Compiler: `-Xlint:all` (see `maven-compiler-plugin`). Coverage: JaCoCo (`jacoco-maven-plugin`); HTML report under `target/site/jacoco` after tests + `jacoco:report`. No separate Checkstyle/SpotBugs in this `pom.xml`. |
| Other | HTTP: Retrofit 3, OkHttp 5, Gson; RxJava 3; Lombok for generated code. |

## Commands (quick reference)

| Command type | Command |
| --- | --- |
| Build | `mvn clean compile` |
| Test | `mvn clean test -DskipTests=false` |
| Lint | `mvn compile` (compiler warnings; see Tech stack) |
| Coverage | `mvn clean test -DskipTests=false jacoco:report` (open `target/site/jacoco/index.html`) |

**Note:** `maven-surefire-plugin` sets `skipTests` to `true` by default in this repo, so you must pass `-DskipTests=false` (or override in your IDE) to run unit tests locally.

**CI:** [.github/workflows/coverage.yml](.github/workflows/coverage.yml) runs `mvn clean package` and JaCoCo steps on push. Other workflows under [.github/workflows/](.github/workflows/) handle publish, scans, and branch checks.

## Where the documentation lives: skills

| Skill | Path | What it covers |
| --- | --- | --- |
| Dev workflow | [skills/dev-workflow/SKILL.md](skills/dev-workflow/SKILL.md) | Branches, Maven lifecycle, CI expectations, PR flow. |
| Contentstack Java CMA SDK | [skills/contentstack-java-cma-sdk/SKILL.md](skills/contentstack-java-cma-sdk/SKILL.md) | Public API, `Contentstack` entry point, auth, versioning boundaries. |
| Java (repo conventions) | [skills/java/SKILL.md](skills/java/SKILL.md) | Package layout, language conventions, Lombok usage. |
| Testing | [skills/testing/SKILL.md](skills/testing/SKILL.md) | Test layout, naming, skipping policy, credentials. |
| Code review | [skills/code-review/SKILL.md](skills/code-review/SKILL.md) | PR checklist and severity guidance. |
| HTTP client stack | [skills/http-client-stack/SKILL.md](skills/http-client-stack/SKILL.md) | Retrofit, OkHttp, retries, logging—what this repo actually wires. |

An index with “when to use” hints is in [skills/README.md](skills/README.md).

## Using Cursor (optional)

If you use *Cursor*, [.cursor/rules/README.md](.cursor/rules/README.md) only points to *AGENTS.md*—same docs as everyone else.

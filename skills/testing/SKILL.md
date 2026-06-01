---
name: testing
description: Use when writing or running tests—Surefire skip flag, test naming, mocks, MockWebServer, and secrets policy.
---

# Testing – Contentstack Management Java SDK

## When to use

- You add or change unit or integration tests under `src/test/java`.
- Tests “do not run” locally or in CI — usually `skipTests`.
- You need to mock HTTP or use OkHttp `MockWebServer` like existing tests.

## Instructions

### Running tests

- **Always** use `mvn test -DskipTests=false` (or `mvn clean test -DskipTests=false`) unless you are only compiling. The Surefire plugin in [`pom.xml`](../../pom.xml) sets `<skipTests>true</skipTests>` by default.

### Layout and naming

- Tests live under `src/test/java/com/contentstack/cms/` with class names matching Surefire includes (`*Test`, `*Tests`, `Test*`, suites like `UnitTestSuite`).
- Reuse helpers such as [`TestClient`](../../src/test/java/com/contentstack/cms/TestClient.java), [`TestUtils`](../../src/test/java/com/contentstack/cms/core/TestUtils.java), or patterns in existing `*APITest` classes when appropriate.

### HTTP and API tests

- **MockWebServer** is available for stubbing HTTP; align with tests in `core/` and `stack/` packages.
- Tests that hit real APIs may require environment variables or `.env` (see `java-dotenv` test dependency in [`pom.xml`](../../pom.xml)). **Never commit** real API keys, management tokens, or passwords; use CI secrets or local env only.

### Coverage

- JaCoCo attaches via `prepare-agent`; generate HTML with `mvn jacoco:report` after a test run with skips disabled.

## References

- [dev-workflow/SKILL.md](../dev-workflow/SKILL.md)
- [http-client-stack/SKILL.md](../http-client-stack/SKILL.md)

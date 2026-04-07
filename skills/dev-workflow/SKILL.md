---
name: dev-workflow
description: Use when branching, building with Maven, CI, or release/publish workflows for this repo.
---

# Dev workflow – Contentstack Management Java SDK

## When to use

- You need the canonical build/test commands or CI expectations.
- You are opening a PR and need branch rules (`master` vs `staging`).
- You are changing `pom.xml`, plugins, or publishing configuration.

## Instructions

### Repository and branches

- Default collaboration flow is documented in [.github/workflows/check-branch.yml](../../.github/workflows/check-branch.yml): PRs targeting `master` from branches other than `staging` may be blocked; prefer the documented Contentstack branching policy for your team.

### Maven

- **Compile:** `mvn clean compile`
- **Tests:** `mvn clean test -DskipTests=false` (Surefire is configured with `skipTests` true by default in [`pom.xml`](../../pom.xml); always pass `-DskipTests=false` for real test runs unless you intentionally skip.)
- **Package:** `mvn clean package` — note tests may be skipped unless you add `-DskipTests=false`.
- **Coverage report:** after tests with skip disabled, `mvn jacoco:report` and open `target/site/jacoco/index.html`.

### CI

- On push, [.github/workflows/coverage.yml](../../.github/workflows/coverage.yml) uses Java 11 on Ubuntu and runs Maven + JaCoCo. Align local verification with those steps when debugging CI-only failures.
- Publishing and security scans live under [.github/workflows/](../../.github/workflows/); read the relevant workflow before changing release or scan behavior.

### PR expectations

- Run tests locally with `-DskipTests=false` before pushing.
- Update [changelog.md](../../changelog.md) or version metadata when your team’s release process requires it.

## References

- [AGENTS.md](../../AGENTS.md)
- [testing/SKILL.md](../testing/SKILL.md)
- [code-review/SKILL.md](../code-review/SKILL.md)

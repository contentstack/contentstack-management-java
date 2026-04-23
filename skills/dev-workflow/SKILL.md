---
name: dev-workflow
description: Use when branching, building with Maven, CI, or release/publish workflows for this repo.
---

# Dev workflow – Contentstack Management Java SDK

## When to use

- You need the canonical build/test commands or CI expectations.
- You are opening a PR and need branch rules (`development` → `master`, GitHub Release publishing).
- You are changing `pom.xml`, plugins, or publishing configuration.

## Instructions

### Repository and branches

- **Flow:** work merges to **`development`**; **release PRs** go **`development` → `master`** (no `staging`). After `master` moves, [.github/workflows/back-merge-pr.yml](../../.github/workflows/back-merge-pr.yml) can open a PR **`master` → `development`** to stay aligned.
- **Releases:** create a **GitHub Release** (triggers [.github/workflows/maven-publish.yml](../../.github/workflows/maven-publish.yml) on **`release: created`**). PRs that change `src/main` or `pom.xml` are checked by [.github/workflows/check-version-bump.yml](../../.github/workflows/check-version-bump.yml) (version + `changelog.md`).

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

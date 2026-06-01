---
name: contentstack-java-cma-sdk
description: Use when changing the Management SDK surface—Contentstack entry, Stack, auth, or CMA integration boundaries.
---

# Contentstack Java CMA SDK – Contentstack Management Java SDK

## When to use

- You add or change public methods on `Contentstack`, `Stack`, organization, user, or other API-facing types under `com.contentstack.cms`.
- You need to document how integrators authenticate (authtoken, management token, OAuth, login).
- You are deciding what belongs in this SDK vs the Delivery API or other Contentstack clients.

## Instructions

### Purpose and boundaries

- This library targets the [Content Management API](https://www.contentstack.com/docs/developers/apis/content-management-api/) (CMA). It is **not** the recommended way to deliver content to websites or apps at scale; the README directs readers to the [Content Delivery API](https://www.contentstack.com/docs/developers/apis/content-delivery-api/) for that.
- The main entry point is [`Contentstack`](../../src/main/java/com/contentstack/cms/Contentstack.java): builders set host, tokens, timeouts, proxy, OAuth, and retry behavior before obtaining `Stack`, `User`, `Organization`, etc.

### Public API and compatibility

- Treat additions to public classes as **semver-sensitive**: new optional builder methods are easier to ship than breaking signature changes.
- Errors and responses should stay consistent with existing patterns (`retrofit2.Response`, model types under `com.contentstack.cms.models`, etc.).
- Document behavior in Javadoc where integrators rely on contracts (auth order, token precedence, branch/early-access headers).

### Versioning

- Artifact coordinates: `com.contentstack.sdk:cms` — version in root [`pom.xml`](../../pom.xml). Follow org release process for bumping version and tagging.

## References

- [java/SKILL.md](../java/SKILL.md)
- [http-client-stack/SKILL.md](../http-client-stack/SKILL.md)
- [Content Management API – Authentication](https://www.contentstack.com/docs/developers/apis/content-management-api/#authentication) (official)

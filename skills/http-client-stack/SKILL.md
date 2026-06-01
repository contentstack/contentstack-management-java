---
name: http-client-stack
description: Use when changing Retrofit, OkHttp, interceptors, retries, proxies, or HTTP logging in this SDK.
---

# HTTP client stack – Contentstack Management Java SDK

## When to use

- You modify [`Contentstack`](../../src/main/java/com/contentstack/cms/Contentstack.java) or related code that builds `Retrofit`, `OkHttpClient`, or interceptors.
- You touch `AuthInterceptor`, OAuth interceptors, logging, connection pool, timeouts, or `RetryConfig` / retry utilities under `com.contentstack.cms.core`.

## Instructions

### Stack in this repo

- **Retrofit 3** + **Gson** converter for API interfaces and JSON binding.
- **OkHttp 5** for transport; **logging-interceptor** for debug logging when enabled.
- **RxJava 3** is a dependency for async patterns where used; keep consistency with existing call patterns.

### Auth and headers

- `AuthInterceptor` and OAuth-related types attach tokens and headers expected by CMA. Changes here affect every call—coordinate with tests in `core/` (e.g. `AuthInterceptorTest`).

### Retries and resilience

- Retry behavior is configurable via `RetryConfig` and related utilities (`core/Retry*.java`). Preserve backward-compatible defaults when adjusting retry conditions or backoff.

### Proxies and TLS

- `Contentstack` supports `Proxy` configuration; verify behavior if you change client building or connection pool settings.

### Testing

- Prefer **MockWebServer** for deterministic HTTP tests; see existing stack and core tests for patterns.

## References

- [contentstack-java-cma-sdk/SKILL.md](../contentstack-java-cma-sdk/SKILL.md)
- [testing/SKILL.md](../testing/SKILL.md)
- [Retrofit](https://square.github.io/retrofit/) (official)
- [OkHttp](https://square.github.io/okhttp/) (official)

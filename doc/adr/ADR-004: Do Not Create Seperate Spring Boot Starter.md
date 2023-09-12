# Do Not Create a Separate Spring Boot Starter

## Status

- ACCEPTED 2023-09-12

## Context

Creating a Spring Boot Starter can streamline the integration of our library into Spring Boot applications by providing
auto-configuration, dependency management, and other features. However, this would require additional development and
maintenance effort.

## Decision

We have decided not to create a separate Spring Boot Starter for our library.

## Rationale

1. **Simplicity:** Keeping everything in one library simplifies the development and release process.
2. **Reduced Maintenance:** A separate starter would require its own maintenance, documentation, and release cycles.
3. **Flexibility:** Users can still manually configure our library if they wish to do so.

## Consequences

1. **Configuration Overhead:** Users might have to write more boilerplate code for configuration.
2. **Adoption Barrier:** A separate starter could potentially speed up the adoption of our library by making it easier to integrate.

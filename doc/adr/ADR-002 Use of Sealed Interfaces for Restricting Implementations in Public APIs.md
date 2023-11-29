# Use of Sealed Interfaces for Restricting Implementations in Public APIs

## Status
Accepted 2023-09-10

## Context
When building libraries that will be used by third-party developers, it's crucial to provide a public API that is both
flexible and reliable. One challenge in designing such an API is how to allow extensibility while preventing misuse.
For instance, a scheduling library could offer various types of scheduling strategies (Cron, Interval, Fixed, etc.),
but not all possible strategies would be supported by the underlying scheduling engine like Dkron.
And this lead to the problem of how to prevent users from implementing their own strategies that are not supported by the platform
and allow extending the library with new strategies that are supported by the platform.

## Decision
We will use Java's sealed interfaces to define components of our public API where we wish to restrict the types of
implementations that can be created. In essence, sealed interfaces enable us to declare an interface or a set of
interfaces while restricting which classes can implement them.

```java
public sealed interface SomeInterface permits Impl1, Impl2 {
    // ...
}
```

This decision has the following benefits:
- Type Safety: Clients can't introduce new types that the library can't handle.
- Encapsulation: Keeps the implementation details inside the library. 
- Clear API Contracts: Makes it clear what kinds of objects the library expects

## Rationale
1. **Prevent Runtime Errors:** A normal interface would allow users to add new implementations, potentially leading to
runtime errors when those types are not handled by the underlying library.
2. **Improve Maintenance:** The restricted set of implementations makes it easier to make changes to the library in the
future as the types that need to be supported are well-defined.
3. **Clarify Usage:** The limitations imposed by a sealed interface serve as implicit documentation, guiding users toward
the intended ways to extend the library.


## Downsides
1. **Limited Extensibility:** Clients cannot extend the sealed interface with their own custom implementations,
2. which may limit some use-cases.
2. **Versioning:** Introducing a new permitted type to a sealed interface would necessitate a major version upgrade for
the library. This is particularly critical for types returned by the library, as it introduces a breaking change for consumers.

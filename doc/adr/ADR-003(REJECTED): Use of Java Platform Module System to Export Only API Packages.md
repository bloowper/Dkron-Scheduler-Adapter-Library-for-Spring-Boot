# Use of Java Platform Module System to Export Only API Packages

## Status

- ACCEPTED 2023-09-01
- REJECTED 2023-09-12 [Reason](#Consequences) point 4

## Context

In a Java library, certain classes and interfaces need to be accessible from a user's perspective, which means they must
be declared as public. We also have internal library components residing in different packages
that need to interact with each other, again requiring the public modifier.
The challenge is to expose only the necessary elements to the end-users while keeping internal details encapsulated.

## Decision

We will use the Java Platform Module System (JPMS) to achieve the desired level of encapsulation. Specifically, we will
define a module-info.java file to export only those packages that are part of the library's public API, typically
designated as the api package.

Example module-info.java:

```java
module myLibrary {
    exports com.myLibrary.api;
}
```

By exporting only the api package, we can restrict the visibility of internal library elements while making all public
elements of the api package available for external use.

## Rationale

1. **Encapsulation:** Using JPMS allows us to hide the internal workings of the library while exposing a clean API.
2. **Ease of Maintenance:** With the internals encapsulated, future changes to the library are less likely to break user
   code.
3. **Modularization:** JPMS enables us to compartmentalize the responsibilities of internal library components into
   separate packages, thereby maintaining a clean and focused public API.

## Consequences

1. **Complexity:** Introducing JPMS can add a level of complexity to the build and deployment process.
2. **Compatibility:** Older systems that do not support JPMS may have issues using the library (older than Java 9).
3. **Learning Curve:** Developers who are not familiar with JPMS might find it slightly harder to understand how to
   develop the library.
4. **Limited Encapsulation for Non-JPMS Users:** If the library users are not employing JPMS in their own projects, they
   will still be able to access public classes that are intended to be internal to the library. This reduces the
   effectiveness of JPMS as an encapsulation tool in such scenarios.



# Organizing Public Elements of Library

## Status

Accepted 2023-09-10

## Context

When creating a library, one of the main concerns is ease of use for the end-users.
The way the library's components are organized plays a significant role in how
intuitive and accessible the library is.

## Decision

We will organize all public elements that are meant to be used by the client into a dedicated subpackage named api.
(excluding published as gradle api)

## Rationale

1. **Discoverability:** Placing all public elements under a single api package makes it easier for users to find the
   functionalities they need.
2. **The api subpackage** serves as a clear boundary that signals what is intended for public use and what is not. This
   limits user confusion about which parts of the library are stable and supported for public use.
3. **Documentation:** With all public APIs in a single package, generating documentation becomes more straightforward.
4. **Future-Proofing:** Future versions of the library can easily indicate changes to the public API by tracking
   modifications to the api package.

## Downsides

1. **Reduced Flexibility for Library Enhancements:** Organizing elements into an api subpackage might discourage
   internal restructuring in the future as it would risk changing the public API and affecting existing users.
2. **Commitment to API Stability:** Once an element is moved to the api package, there is an implicit promise of
   stability, making it difficult to make breaking changes.
3. **Potential for Clutter:** Over time, the api package might become cluttered if not properly maintained, reducing its
   initial benefits of clarity and discoverability.

## Updates

- `2023-09-24`
  `EnableDkronScheduler` is an exception to this because it needs access to the configuration class of the library while
  also being used by the library's client.

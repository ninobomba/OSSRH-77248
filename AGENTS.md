# AGENTS.md

## Project Context
**Tools for Monkeys (T4M)** is a Java Commons library providing utilities for ID generation, API responses, persistence, and more.
- **Java Version**: 21 (Uses modern features like records, sealed interfaces, switch expressions)
- **Build System**: Maven
- **Frameworks**: Spring Boot (components), Lombok, Jackson

## Essential Commands

### Build
Use the provided script or Maven directly. Note the parallel build and skip tests flags often used.
```bash
# Recommended build command (skips tests)
mvn clean install -DskipTests=true -T 1C

# Via script
./mvn-install.sh
```

### Test
```bash
# Run all tests
mvn test

# Run specific test class
mvn -pl :t4m-commons -am test -Dtest=JsonUtilsTest
```

## Code Style & Conventions
**CRITICAL**: This project uses a distinct spacing style that you **MUST** preserve when editing files.

### Formatting Rules
- **Spaces inside parentheses**: `if ( condition )`, `method ( arg )`
- **Spaces around generics**: `List < String >`, `class MyClass < T >`
- **Spaces in records/constructors**: `record Success < T >( T data )`
- **Lombok**: Heavily used (`@Getter`, `@EqualsAndHashCode`, `@Builder`).
- **Final**: Classes are often `final`.

**Example**:
```java
public sealed interface OperationResult < T >
    permits OperationResult.Success, OperationResult.Failure {

    static < T > OperationResult < T > success ( T value ) {
        return new Success <> ( value );
    }
}
```

### Architecture Patterns
- **Result Pattern**: Uses `OperationResult<T>` (sealed interface) instead of exceptions for flow control.
- **Exceptions**: Prefers `RuntimeException` hierarchies located in `io.github.ninobomba.commons.exceptions`.
- **Singletons/Factories**: Common patterns for utilities (e.g., `IdGeneratorSnowFlakeSupport.getINSTANCE()`).

## Testing Strategy
- **Framework**: JUnit 5 + AssertJ + Mockito.
- **Visibility**: Test classes and methods are typically package-private (default visibility).
- **Assertions**: Mix of JUnit assertions (`assertNotNull`), AssertJ, and Java `assert` keyword.
- **Location**: `src/test/java` mirroring the source package structure.

## Gotchas & Observations
1.  **Missing Packages**: The README mentions a `notification` feature, but the package seems missing from `src/main/java`. Verify existence before referencing.
2.  **Commented Tests**: Some tests may be commented out (e.g., in `IdGeneratorSnowFlakeSupportTest`). Check `git blame` or context before enabling.
3.  **Strict Formatting**: The Edit tool may fail if you don't match the extensive whitespace exactly. **Always read the file first** and copy the exact spacing.
4.  **Java 21**: You can and should use modern Java features (records, patterns).

## Directory Structure
- `src/main/java/io/github/ninobomba/commons/`
    - `api/`: API response wrappers, geolocation.
    - `data/`: Auditable entities, DTOs, Mappers.
    - `id/`: ID generators (Snowflake, UUID, etc.).
    - `patterns/`: Design patterns (OperationResult, Lazy).
    - `exceptions/`: Centralized exception handling.
    - `web/`: HTTP and User Agent utilities.
- `scripts/`: Deployment and utility scripts.

# OSSRH-77248 - Tools for Monkeys (T4M)

T4M is a comprehensive set of common utilities for Java projects, designed to simplify development and provide reusable components for common tasks.

## Project Overview

- **Name**: Tools for Monkeys (T4M)
- **Group ID**: io.github.ninobomba
- **Artifact ID**: t4m-commons
- **Version**: 1.0.0.37
- **License**: Apache License 2.0
- **Java Version**: 21
- **Maven Central**: https://central.sonatype.com/artifact/io.github.ninobomba/t4m-commons/versions

## Key Features

### ID Generation
- Snowflake algorithm-based ID generator
- HashSet-based ID generator
- Pre-generation and caching for performance

```java
long id = IdGeneratorSnowFlakeSupport.getINSTANCE().getNextId();
```

### Auditable Entities
- Support for tracking creation and modification timestamps and users
- Automatic field population based on CRUD operations

### Notifications
- Multi-channel notification system (Email, Slack, Twilio SMS, WhatsApp)
- Centralized notification management

```java
NotificationManager.push(
    NotificationMessage
        .builder()
        .message("Hello, I love you, won't you tell me your name?")
        .payload("optional")
        .build()
);
NotificationManager.flush();
```

### Events
- Event tracking with timestamps and elapsed time
- JSON serialization for logging and analysis

```java
Request request = Request.builder()
    .name("Payment Process")
    .payload("{}")
    .build();

request.pushEvent("start");
request.pushEvent("processing");
request.pushEvent("complete");

System.out.println(request.toJsonString(true));
```

### Checkpoints
- Define and track progress through a process
- JSON-based checkpoint configuration

### Web Utilities
- HTTP request/response utilities
- User agent detection
- Support for both Jakarta and Javax APIs

### API Utilities
- API response builders
- HATEOAS support
- Geolocation and IP utilities

### Exception Handling
- Comprehensive exception hierarchy
- Factory pattern for exception creation
- Localized exception messages

### Other Utilities
- JSON processing
- Text manipulation
- Time utilities
- Validation
- Spring integration
- Design pattern implementations

## Architecture

The library is organized into logical packages:

- **api**: API-related utilities (geolocation, IP, response builders)
- **architecture**: Architectural patterns and adapters
- **catalogs**: Catalog-related utilities (financial, numeric)
- **checkpoints**: Checkpoint tracking
- **constants**: Common constants
- **data**: Data-related utilities (auditable entities, DTOs, ETL, mappers)
- **errors**: Error handling
- **events**: Event handling
- **exceptions**: Exception definitions and handling
- **id**: ID generation
- **json**: JSON utilities
- **patterns**: Design pattern implementations
- **persistence**: Data persistence utilities
- **platform**: Platform-specific utilities
- **properties**: Property handling
- **spring**: Spring Framework integration
- **text**: Text processing utilities
- **time**: Time-related utilities
- **validator**: Validation utilities
- **web**: Web-related utilities (HTTP, user agent)

## Getting Started

Add the dependency to your Maven project:

```xml
<dependency>
    <groupId>io.github.ninobomba</groupId>
    <artifactId>t4m-commons</artifactId>
    <version>1.0.0.37</version>
</dependency>
```

## Installation

The library is available on Maven Central. No additional repositories are needed.

## Requirements

- Java 21 or higher
- Maven 3.6 or higher

## Dependencies

The library depends on several key libraries:
- Spring Framework
- Jackson for JSON processing
- Apache Commons
- Lombok (for development only)
- Snowflake ID generator
- User agent detection libraries

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome. Please feel free to submit a Pull Request.

## Contact

- **Developer**: Fernando Farfan
- **GitHub**: https://github.com/ninobomba

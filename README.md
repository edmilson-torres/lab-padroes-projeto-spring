# Spring Design Patterns Lab

A Spring Boot project demonstrating practical implementation of design patterns, integrating with ViaCEP API and H2 Database.

## Design Patterns Implemented

- **Singleton**: Utilized through Spring's dependency injection
- **Strategy/Repository**: Implemented in the data access layer
- **Facade**: REST Controller abstracting the complexity of integrations

## Technologies

- Java 11
- Spring Boot 2.5.4
- Spring Data JPA
- Spring Cloud OpenFeign
- H2 Database
- OpenAPI/Swagger UI

## Features

- Complete CRUD operations for Client management
- Integration with ViaCEP API for address lookup
- In-memory H2 database
- RESTful API endpoints
- API documentation with OpenAPI

## API Endpoints

- GET /clientes - List all clients
- GET /clientes/{id} - Get client by ID
- POST /clientes - Create new client
- PUT /clientes/{id} - Update existing client
- DELETE /clientes/{id} - Delete client

## Running the Project

1. Clone the repository
2. Build with Maven:
```bash
mvn clean install
```
3. Run the application:
```bash
mvn spring-boot:run
```

## API Documentation

Access the Swagger UI documentation at:
```
http://localhost:8080/swagger-ui.html
```

## Project Structure

- Controller layer: REST API facade pattern
- Service layer: Business logic implementation
- Repository layer: Data access with JPA
- Model layer: Entity classes
- Integration: ViaCEP service with OpenFeign

## Dependencies

The project uses Spring Cloud for OpenFeign client and Spring Data JPA for database operations. Full dependency list available in pom.xml.

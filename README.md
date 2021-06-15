##### legendary-succotash

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Dauren-Delmukhambetov_legendary-succotash&metric=alert_status)](https://sonarcloud.io/dashboard?id=Dauren-Delmukhambetov_legendary-succotash)

# Users API

#### Short Description
You need to build REST API with authentication, which allows you to do simple CRUD operations with role permissions.

#### Topics
- API
  - Documentation
  - RESTful Web Services
 - Database
   - Migrations
   - Design
 - Security
   - Role-based
   - JWT
 - Validation
 
#### Requirements
 - Java
 - Spring Boot
 - MySQL or PostgreSQL

 1. Create signin, signup and refresh operations using: user. Authentication should be implemented using Spring Security and should be based on JWT tokens (access and refresh).
 2. Create CRUD operations for the next entity: user. Get users should return page of requested pageNumber and pageSize. Also, this operation should return only truncated users data. Classic delete should be replaced by soft delete.
 3. There should be 2 types of users: "ADMIN" and "USER". "ADMIN" must be inserted via script. Only admins can use all CRUD operations. Admin should be able to view ALL users with any role, even deleted. Likewise should not be possible to signup with role admin.
 4. "USER" should be able to use all auth operations, should view only existed users with role, not deleted. Could update, delete and view by id only himself.
 5. Use Gradle as build automation tool. 
 6. Use Swagger for API documentation.
 7. Use Flyway for database migrations.
 8. Get users should return page of requested pageNumber and pageSize.
 9. Data validation should be implemented.
 
#### Testing
Use Swagger for manual testing.

#### Technologies
 - Spring
   - Boot
   - Security
   - Data JPA
 - PostgreSQL
 - Flyway
 - Gradle
 - Swagger

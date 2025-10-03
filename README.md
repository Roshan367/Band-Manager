# Band-Manager
## Project Summary
This project is a web application to band memebers, loaned instruments, music parts/sets, and performance schedules for a senior and training band.

The system allows for the members in the band to have real-time updates of band inventories, member needs, and rehearsal/performance planning.

There are three main user-types, Director, Committee Member and Member. Directors have all permissions of the system. Committee Members can only add/edit/delete items from the instrument and music inventories, as well as create performances. Members can loan instruments, request music pieces and update thier performance availability. Additionally, memebers can be used as a proxy for their children who are members in a band. The children proxies are able to request needed music pieces as well as update their performance availability.

Contains both user authentication and authorisation for security. Additionally, parametrised queries are used to prevent SQL-injection.

## Setup
- Requirements:
  - Gradle 9.1.0
  - PostgreSQL 17.6
  - Java (openJDK) 25.0.1

Make sure to setup your own PosgreSQL database and setup the .env file to work with the application.properties file.

```
.env
DB_USER=username
DB_PASSWORD=password
```
```
application.properties
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```

Now add the senior-band and training-band values to the bands table in the database.

`sudo -iu postgres`

`psql`

`\c band_manager_db_name`

```
-- Add the senior band
INSERT INTO bands (name)
VALUES ('senior-band');

-- Add the training band
INSERT INTO bands (name)
VALUES ('training-band');
```

Now that the bands are added start the application with

`./gradlew bootRun`

Now we sign up and exit the application

With this created user we can now give them committee member and director permissions (This is only necessary for the original director user)

```
-- Add committee member role
INSERT INTO users (user_id, roles)
VALUES (1, 'COMMITTEE_MEMBER');

-- Add director role
INSERT INTO users (user_id, roles)
VALUES (1, 'DIRECTOR');
```

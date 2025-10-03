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
  - 

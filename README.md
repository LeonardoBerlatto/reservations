# reservations service

## 🔮 My solution
To solve this problem I used the following steps:
1. Mapped the entities (reservation)
2. The VOs (userInfo and vacancy)
3. Created the db scripts (check [db](db))
4. Created an empty spring project
5. Configured swagger and spring actuator
6. Added Spring Data JPA and some other dependencies for fast development
7. Created the endpoints respecting the constraints while writing the tests
8. Did some manual testing (using Postman)
9. Added the reservation service to the docker-compose file
10. Added the makefile to make things easier
11. Added more API docs ([Postman Collection](/postman/Reservations.postman_collection.json) and README)

## 🤔 Why SQL?
I chose SQL because it's easier to work with and it's more reliable than NoSQL.
<br>
And, since the text mentioned to take extra care with performance, I thought that SQL would be a better choice.

## ⏯️ Running the code
1. Build with Gradle
2. Deploy a container with postgres on port 5432(check [application-dev.yml](src/main/resources/application-dev.yaml))
3. **Run the application using dev profile**
4. Or just run `make run` and it will run the application on a container for you with Postgres on another

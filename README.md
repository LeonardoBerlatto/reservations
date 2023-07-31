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
11. Added more API docs (Postman Collection and README)

## 🤔 Why SQL?
I chose SQL because it's easier to work with and it's more reliable than NoSQL.
<br>
And, since the text mentioned to take extra care with performance, I thought that SQL would be a better choice **for this case**.

## ⏲️ Things I'd improve with more time
1. Implement clean architecture for real
2. Decouple the domain from spring data (create JPA entity on another class on adapter package)
3. Move @Transactional to the use case (application) layer
4. Add cache to /vacancies endpoint since it must be the most called endpoint
5. Use domain-specific exceptions and RestException only on the adapter layer 
6. 6.Add workflow to publish docker image when pushing to main branch

## ⏯️ Running the code
1. Build with Gradle
2. Deploy a container with postgres on port 5432(check [application-dev.yml](src/main/resources/application-dev.yaml))
3. **Run the application using dev profile**
4. Or just run `make run` and it will run the application on a container for you with Postgres on another

## 📝 API Docs
You can find the API docs on [swagger](http://localhost:8080/api/swagger-ui.html)

You can also find a postman collection [here](/postman/Reservations.postman_collection.json)

## 🗃️ Database
You can find the database scripts [here](/db)
I wrote the scripts using Postgres syntax, but it works with H2 as well

## ✔️ Tests
I covered 100% of the business logic with unit tests and all the endpoints with integration tests <br>
Tests wrote using:
* jUnit
* Mockito
* MockMvc
<br>

You can find the tests [here](/src/test/)

## 🪛 CI/CD
I used GitHub Actions to build and test the code on every push to any dev branch.

<br>

This is a good practice to avoid discovering errors on the last minute and to make sure that the code is always working.

<br>

Check the build workflow [here](/.github/workflows/on-push.yml)

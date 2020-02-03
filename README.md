# Demo API


## Overview  

This api is to facilitate the work distribution among the agents based on the skills of agents

## Run Server On Docker


```
1. ./gradlew build

2. docker-compose up
```

This starts the server and mysql 8.0.19 on docker.
This also creates and initializes the database with agents skills.

Api endpoints can be tested with postman or curl or swagger.

Access the swagger doc with the following url for details
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


### Local deployment

This uses H2 database and runs on 8081 port and initializes the in memory db with agent skills
To set up your key run:

```
./gradlew bootRun
```

## Endpoints



Assign tasks using curl
```
curl -X POST "http://localhost:8080/assign" -H "accept: */*" -H "Content-Type: application/json" -d "{ "complete": "no", "priority": "high", "skills": [ "skill1", "skill2" ], "taskName": "skill1and2"}"
```

Complete the task 
```
curl -X PATCH "http://localhost:8080/task/6" -H "accept: */*"
```

Get all in progress tasks
```
curl -X GET "http://localhost:8080/tasks" -H "accept: */*" 
```

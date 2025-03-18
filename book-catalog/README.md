
# Project Details

## Setup Containers

All done inside main project folder.

```bash
mvn clean package -DskipTests
```

```bash
podman build -t book-catalog -f Dockerfile.dockerfile
```

```bash
podman network create mynetwork
```

```bash
podman run -d --name postgresdb --network mynetwork -e POSTGRES_DB=postgres -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -p 6432:5432 postgres:17.4-alpine
```

```bash
podman run -d -p 9092:9092 --name broker --network mynetwork -e KAFKA_NODE_ID=1 -e KAFKA_PROCESS_ROLES=broker,controller -e KAFKA_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://broker:9092 -e KAFKA_CONTROLLER_LISTENER_NAMES=CONTROLLER -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT -e KAFKA_CONTROLLER_QUORUM_VOTERS=1@localhost:9093 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -e KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR=1 -e KAFKA_TRANSACTION_STATE_LOG_MIN_ISR=1 -e KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS=0 -e KAFKA_NUM_PARTITIONS=1 apache/kafka:latest
```

```bash
podman run --name book-catalog --network mynetwork -p 8080:8080 book-catalog
```

## Use microservice

It can be used as is. Every request to the API requires the use of a JWT token in the ```auth``` header that can be acquired with a post to the ```/auth/login``` endpoint.

For testing purposes the following JWT key can be used:

```bash
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIn0.g8mgaoLkX46bCr6BxaMbqMb6T83GN4I6e_t_jWULx94
```

## Endpoints exposed

* ```POST /books``` Add a new book to the catalog.
* ```GET /books``` Retrieve a list of available books.
* ```GET /books/{bookId}``` Retrieve details of a specific book by book ID.
* ```PUT /books/{bookId}``` Update details of a specific book by book ID.
* ```POST /auth/login``` Retrieve JWT token used for authentication.

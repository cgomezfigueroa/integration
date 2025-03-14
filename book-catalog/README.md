
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
podman run --name postgresdb --network mynetwork -e 
```

```bash
POSTGRES_DB=postgres -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -p 6432:5432  postgres:17.4-alpine
```

```bash
podman run --name book-catalog --network mynetwork -p 8080:8080 book-catalog
```

## Missing Features

* Volume integration
* Kafka Support
* Conversion to single Pod

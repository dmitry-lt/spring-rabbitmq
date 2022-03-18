# spring-rabbitmq

This sample demonstrates RabbitMQ producer and consumer Spring Boot applications

### Prerequisites

Must be installed locally

* Java 17
* Docker

### Local run

#### To start RabbitMQ docker container

`docker-compose up`

RabbiMQ Management console will be available at http://localhost:15672/
Username / password: guest / guest

#### To start producer

```
cd producer
gradlew bootRun
```

#### To start consumer

```
cd consumer
gradlew bootRun
```


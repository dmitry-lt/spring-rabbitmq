# spring-rabbitmq

This sample demonstrates:
* RabbitMQ producer and consumer Spring Boot applications
* unit and integration tests
* code coverage with jacoco

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

### Description

This sample uses a topic exchange. More on exchange types: https://www.rabbitmq.com/tutorials/amqp-concepts.html#exchanges
The topic exchange is bound to a queue, which is listened by consumer

At startup, producer sends a message saying that it started

Later on, producer sends a message if it is pinged

`curl localhost:8081/ping`

#### Notes
For demo purposes, there are certain limitations:
* Producer creates topic exchange, queue and binding between them. In a real world application this logic can be decoupled from producer
* Start order matters: first RabbitMQ, then producer, then consumer
* Integration tests require running RabbitMQ

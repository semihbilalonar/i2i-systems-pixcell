# Pixcell Kafka Message Processing

This project provides Kafka-based message processing components for Pixcell, including custom serializers, deserializers, and both publisher and subscriber mechanisms.

## Components

### Message Types

- **BalanceMessage**: Handles balance updates (e.g., SMS, DATA).
- **NotificationMessage**: Contains user details and balance information.
- **UsageRecordMessage**: Records service usage (e.g., SMS, DATA).
- **WalletMessage**: Manages wallet balance updates.

### Serializers

- **BalanceMessageSerializer**
- **NotificationMessageSerializer**
- **UsageRecordMessageSerializer**
- **WalletMessageSerializer**

### Deserializers

- **BalanceMessageDeserializer**
- **NotificationMessageDeserializer**
- **UsageRecordMessageDeserializer**
- **WalletMessageDeserializer**

## Publisher

The `Publisher` class sends messages to Kafka topics.

#### Usage

```java
Publisher<BalanceMessage> publisher = new Publisher<>();
publisher.createBalanceMessageProducer();
BalanceMessage message = new BalanceMessage(BalanceType.DATA, 12345678901L, 80, 10);
publisher.send(message, "balance-topic");
publisher.close();
```

## Subscriber
- The `Subscriber` class consumes messages from Kafka topics.

```java

Subscriber<BalanceMessage> subscriber = new Subscriber<>();
subscriber.createBalanceMessageConsumer();
ConsumerRecords<String, BalanceMessage> records = subscriber.poll();
for (ConsumerRecord<String, BalanceMessage> record : records) {
    System.out.println("Received message: " + record.value());
}
```

## Configuration
- Set the Kafka broker URL in config.properties:

```
kafka.url=your-kafka-broker-url
```
![image](https://github.com/user-attachments/assets/bfbd972e-32b3-4053-8a50-439d645e14a6)
![image](https://github.com/user-attachments/assets/64308668-f476-4a87-bf77-6d8d67e21c34)
![image](https://github.com/user-attachments/assets/67b424c9-e401-4816-8bbf-b43df503ada0)
![image](https://github.com/user-attachments/assets/e09583d8-4942-4459-aaba-4d1ed3e12157)




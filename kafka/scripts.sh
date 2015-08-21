#Start Zookeeper:
./bin/zookeeper-server-start.sh config/zookeeper.properties

#Start Kafka:
./bin/kafka-server-start.sh config/server.properties

#Start a consumer:
./bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test-topic

#Start a producer:
./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test-topic

#Start a remote producer:
./bin/kafka-console-producer.sh --broker-list my.domain:9092 --topic test-topic


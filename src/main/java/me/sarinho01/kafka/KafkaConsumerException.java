package me.sarinho01.kafka;

public class KafkaConsumerException extends RuntimeException{
    public KafkaConsumerException(String message) {
        super(message);
    }
}

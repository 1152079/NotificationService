package com.example.notificationservice.websocket;

import com.example.notificationservice.rabbitmq.RabbitMQConfiguration;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQWebSocketHandler {
    private final SimpMessagingTemplate messagingTemplate;

    public RabbitMQWebSocketHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = RabbitMQConfiguration.userQueueName)
    public void handleUserQueue(String message) {
        logAndSend("/topic/user", message);
    }

    @RabbitListener(queues = RabbitMQConfiguration.boatStatusQueueName)
    public void handleRentalQueue(String message) {
        logAndSend("/topic/boat", message);
    }

    @RabbitListener(queues = RabbitMQConfiguration.bookingStatusQueueName)
    public void handleBookQueue(String message) {
        logAndSend("/topic/booking", message);
    }

    private void logAndSend(String destination, String message) {
        try {
            System.out.println("Sending message to " + destination + ": " + message);
            messagingTemplate.convertAndSend(destination, message);
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}
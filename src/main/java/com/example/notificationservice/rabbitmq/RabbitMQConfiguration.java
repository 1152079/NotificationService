package com.example.notificationservice.rabbitmq;

import com.example.notificationservice.rabbitmq.component.BoatStatusQueueReceiver;
import com.example.notificationservice.rabbitmq.component.BookingQueueReceiver;
import com.example.notificationservice.rabbitmq.component.UserQueueReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String userQueueName = "userQueue";
    public static final String bookingStatusQueueName = "bookingStatusQueue";
    public static final String boatStatusQueueName = "boatStatusQueue";
    static final String exchangeName = "testExchange";

    @Bean
    Queue myQueue() {
        return new Queue(userQueueName, false);
    }

    @Bean
    Queue bookingQueue() {
        return new Queue(bookingStatusQueueName, false);
    }

    @Bean
    Queue boatStatusQueue() {
        return new Queue(boatStatusQueueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName, false, false);
    }

    @Bean
    Binding binding(Queue myQueue, TopicExchange exchange) {
        return BindingBuilder.bind(myQueue).to(exchange).with("my.key");
    }

    @Bean
    Binding bindingBooking(Queue bookingQueue, TopicExchange exchange) {
        return BindingBuilder.bind(bookingQueue).to(exchange).with("bookingStatus.key");
    }

    @Bean
    Binding bindingBoatStatus(Queue boatStatusQueue, TopicExchange exchange) {
        return BindingBuilder.bind(boatStatusQueue).to(exchange).with("boatStatus.key");
    }

    @Bean
    public SimpleMessageListenerContainer userQueueContainer(
            @Qualifier("userQueueReceiver") UserQueueReceiver userQueueReceiver,
            ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(new MessageListenerAdapter(userQueueReceiver, "receiveMessage"));
        container.setQueueNames(userQueueName);
        return container;
    }

    @Bean
    public SimpleMessageListenerContainer bookingQueueContainer(
            @Qualifier("bookingQueueReceiver") BookingQueueReceiver bookingQueueReceiver,
            ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(new MessageListenerAdapter(bookingQueueReceiver, "receiveMessage"));
        container.setQueueNames(bookingStatusQueueName);
        return container;
    }

    @Bean
    public SimpleMessageListenerContainer boatStatusQueueContainer(
            @Qualifier("boatStatusQueueReceiver") BoatStatusQueueReceiver boatStatusQueueReceiver,
            ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(new MessageListenerAdapter(boatStatusQueueReceiver, "receiveMessage"));
        container.setQueueNames(boatStatusQueueName);
        return container;
    }

    @Bean(name = "userQueueReceiverBean")
    public MessageListenerAdapter UserQueueReceiver(UserQueueReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean(name = "bookingQueueReceiverBean")
    public MessageListenerAdapter bookingQueueReceiver(BookingQueueReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean(name = "boatStatusQueueReceiverBean")
    public MessageListenerAdapter boatStatusQueueReceiver(BoatStatusQueueReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}

package com.example.notificationservice.rabbitmq.component;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class UserQueueReceiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received from userQueue: " + message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {return latch;}

}

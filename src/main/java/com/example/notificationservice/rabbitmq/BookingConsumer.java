package com.example.notificationservice.rabbitmq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.notificationservice.models.enums.BoatStatus;
import com.example.notificationservice.repositories.BoatRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class BookingConsumer {

    private final BoatRepository boatRepository;
    private final ObjectMapper objectMapper;

    public BookingConsumer(BoatRepository boatRepository, ObjectMapper objectMapper) {
        this.boatRepository = boatRepository;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "bookingStatusQueue")
    public void processBookingMessage(String message) {
        try {
            Map<String, String> bookingData = objectMapper.readValue(message, new TypeReference<>() {});

            String bookingId = bookingData.get("bookingId");
            String boatId = bookingData.get("boatId");
            String status = bookingData.get("status");

            System.out.println("Получено сообщение: " + bookingData);

            var boat = boatRepository.findById(UUID.fromString(boatId))
                    .orElseThrow(() -> new IllegalArgumentException("Лодка с ID " + boatId + " не найдена."));

            if ("CONFIRMED".equalsIgnoreCase(status)) {
                boat.setStatus(BoatStatus.BOOKED);
                boatRepository.save(boat);
                System.out.println("Статус лодки с ID " + boatId + " обновлён на 'BOOKED' для заказа " + bookingId);
            } else {
                System.out.println("Некорректный статус: " + status);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при обработке сообщения: " + e.getMessage());
        }
    }
}

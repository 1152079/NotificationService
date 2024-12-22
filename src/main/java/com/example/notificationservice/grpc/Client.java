package com.example.notificationservice.grpc;

import com.example.boatrental.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class Client {
    private final UserServiceGrpc.UserServiceBlockingStub userService;
    private final BoatServiceGrpc.BoatServiceBlockingStub bookService;
    private final BookingServiceGrpc.BookingServiceBlockingStub bookingService;
    public Client() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        userService = UserServiceGrpc.newBlockingStub(channel);
        bookService = BoatServiceGrpc.newBlockingStub(channel);
        bookingService = BookingServiceGrpc.newBlockingStub(channel);
    }
    public UserResponse findUser(String name) {
        UserNameRequest request = UserNameRequest.newBuilder().setName(name).build();
        return userService.findUser(request);
    }
    public UserListResponse getAllUsers() {
        return userService.getAllUsers(EmptyRequest.newBuilder().build());
    }
    public BoatResponse findBoat(String name) {
        BoatNameRequest request = BoatNameRequest.newBuilder().setName(name).build();
        return bookService.findBoat(request);
    }
    public BoatListResponse getAllBoats() {
        return bookService.getAllBoats(EmptyRequest.newBuilder().build());
    }
    public BookingResponse getBooking(String bookingId) {
        BookingRequest request = BookingRequest.newBuilder().setId(bookingId).build();
        return bookingService.getBooking(request);
    }
    public BookingListResponse getAllBookings() {
        return bookingService.getAllBookings(EmptyRequest.newBuilder().build());
    }
    public BookingCheckResponse addBooking(String userId, String boatId) {
        BookingCheckRequest request = BookingCheckRequest.newBuilder()
                .setUser(userId)
                .setBoat(boatId)
                .build();
        return bookingService.addBooking(request);
    }

    public static void main(String[] args) {
        Client client = new Client();

        UserResponse user1 = client.findUser("Мешкова Александра Станиславовна");
        System.out.println("User found: ");
        System.out.println("Name: " + user1.getName());
        System.out.println("Email: " + user1.getEmail());
        System.out.println("Phone: " + user1.getPhone());
        System.out.println("Role: " + user1.getRole());

        UserListResponse users = client.getAllUsers();
        users.getUsersList().forEach(u -> {
            System.out.println("User: ");
            System.out.println("Name: " + u.getName());
            System.out.println("Email: " + u.getEmail());
            System.out.println("Phone: " + u.getPhone());
            System.out.println("Role: " + u.getRole());
        });

        BoatResponse boat1 = client.findBoat("Катер GD-4123");
        System.out.println("Boat found: ");
        System.out.println("Name: " + boat1.getName());
        System.out.println("Description: " + boat1.getDescription());
        System.out.println("Type: " + boat1.getType());
        System.out.println("Capacity: " + boat1.getCapacity());
        System.out.println("Status: " + boat1.getStatus());
        System.out.println("Price per hour: " + boat1.getPricePerHour());

        BoatListResponse boats = client.getAllBoats();
        boats.getBoatsList().forEach(b -> {
            System.out.println("Boat: ");
            System.out.println("Name: " + b.getName());
            System.out.println("Description: " + b.getDescription());
            System.out.println("Type: " + b.getType());
            System.out.println("Capacity: " + b.getCapacity());
            System.out.println("Status: " + b.getStatus());
            System.out.println("Price per hour: " + b.getPricePerHour());
        });

        BookingListResponse bookings = client.getAllBookings();
        bookings.getBookingsList().forEach(b -> {
            System.out.println("Booking: ");
            System.out.println("ID: " + b.getId());
            System.out.println("User ID: " + b.getUserId());
            System.out.println("Boat ID: " + b.getBoatId());
            System.out.println("Start time: " + b.getStartTime());
            System.out.println("End time: " + b.getEndTime());
            System.out.println("Status: " + b.getStatus());
            System.out.println("Total price: " + b.getTotalPrice());
            System.out.println("Created at: " + b.getCreatedAt());
            System.out.println("Updated at: " + b.getUpdatedAt());
        });

        BookingResponse booking = client.getBooking("9dfa39a6-c3e5-49d5-8513-64790101d4c3");
        System.out.println("Booking found: ");
        System.out.println("ID: " + booking.getId());
        System.out.println("User ID: " + booking.getUserId());
        System.out.println("Boat ID: " + booking.getBoatId());
        System.out.println("Start time: " + booking.getStartTime());
        System.out.println("End time: " + booking.getEndTime());
        System.out.println("Status: " + booking.getStatus());
        System.out.println("Total price: " + booking.getTotalPrice());
        System.out.println("Created at: " + booking.getCreatedAt());
        System.out.println("Updated at: " + booking.getUpdatedAt());

        BookingCheckResponse bookingCheck = client.addBooking("1036535e-b9b9-4306-969e-3581555524ed", "9cf39d2f-f735-40f0-bc0b-0a6da3395770");
        System.out.println("Booking status: " + bookingCheck.getMessage());


        bookings.getBookingsList().forEach(b -> {
            System.out.println("Booking: ");
            System.out.println("ID: " + b.getId());
            System.out.println("User ID: " + b.getUserId());
            System.out.println("Boat ID: " + b.getBoatId());
            System.out.println("Start time: " + b.getStartTime());
            System.out.println("End time: " + b.getEndTime());
            System.out.println("Status: " + b.getStatus());
            System.out.println("Total price: " + b.getTotalPrice());
            System.out.println("Created at: " + b.getCreatedAt());
            System.out.println("Updated at: " + b.getUpdatedAt());
        });


    }
}
package com.chihiro.booking.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequestDTO {
    private String name;
    private String email;
    private LocalDateTime dateTime;
    private int numberOfPeople;
}

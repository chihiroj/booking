package com.chihiro.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingResponseDTO {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime dateTime;
    private int numberOfPeople;
    private Status status;
}

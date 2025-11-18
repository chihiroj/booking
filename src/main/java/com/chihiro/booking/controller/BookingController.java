package com.chihiro.booking.controller;

import com.chihiro.booking.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingRepository bookingRepository;

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();

        List<BookingResponseDTO> bookingResponseDTOs = bookings.stream()
                .map(booking -> new BookingResponseDTO(
                        booking.getId(),
                        booking.getName(),
                        booking.getEmail(),
                        booking.getDateTime(),
                        booking.getNumberOfPeople(),
                        Status.valueOf(booking.getStatus())
                ))
                .toList();

        return ResponseEntity.ok(bookingResponseDTOs);
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Booking not found"));
        BookingResponseDTO dto = new BookingResponseDTO(
                booking.getId(),
                booking.getName(),
                booking.getEmail(),
                booking.getDateTime(),
                booking.getNumberOfPeople(),
                Status.valueOf(booking.getStatus())
        );
        return ResponseEntity.ok(dto);

    }

    @PostMapping("/bookings")
    public ResponseEntity<String> postBooking(@RequestBody BookingRequestDTO dto){
        Booking booking = new Booking();
        booking.setName(dto.getName());
        booking.setEmail((dto.getEmail()));
        booking.setDateTime(dto.getDateTime());
        booking.setNumberOfPeople(dto.getNumberOfPeople());
        booking.setStatus(Status.PENDING.name());

        bookingRepository.save(booking);

        return ResponseEntity.ok("Booking saved");
    }

    @PutMapping("/bookings/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable Long id, @RequestBody BookingRequestDTO dto){
        Booking booking = bookingRepository.findById(id).orElseThrow(() ->new RuntimeException("Booking not found"));

        booking.setName(dto.getName());
        booking.setEmail((dto.getEmail()));
        booking.setDateTime(dto.getDateTime());
        booking.setNumberOfPeople(dto.getNumberOfPeople());

        bookingRepository.save(booking);
        return ResponseEntity.ok("Booking updated");
    }

    @DeleteMapping("bookings/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id){

        if(!bookingRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        bookingRepository.deleteById(id);
        return ResponseEntity.ok("Booking deleted");
    }
}

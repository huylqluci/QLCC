package com.booking.homestay.shared.controllers;

import com.booking.homestay.employee.dto.BookingResponse;
import com.booking.homestay.employee.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingSharedControllers {

    private final BookingService bookingService;

    @GetMapping("/paypal/{id}")
    public ResponseEntity<BookingResponse> paypalBooking(@PathVariable Long id) {
        return status(HttpStatus.OK).body(bookingService.getCheckBooking(id));
    }

    @GetMapping("/submitpaypal/{id}")
    public ResponseEntity<String> submitPaypal(@PathVariable Long id) {
        bookingService.submitBooking(id);
        return new ResponseEntity<>("Bạn đã thanh toán thành công tiền cọc", OK);
    }

    @GetMapping("/seachbooking/{phone}&{idBook}")
    public ResponseEntity<List<BookingResponse>> seachBooking(@PathVariable String phone, @PathVariable Long idBook) {
        return status(HttpStatus.OK).body(bookingService.seachBooking(phone, idBook));
    }

}

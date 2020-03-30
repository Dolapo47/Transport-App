package transport.app.demo.controller;

import org.apache.tika.metadata.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transport.app.demo.model.Booking;
import transport.app.demo.responses.Response;
import transport.app.demo.service.BookingService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<?>createBooking(@Valid @RequestBody Booking booking, HttpServletRequest request){
        bookingService.createBooking(booking, request);
        Response<Booking> response = new Response<>(HttpStatus.CREATED);
        response.setMessage("Successfully created a booking");
        response.setData(booking);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

}

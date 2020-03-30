package transport.app.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import transport.app.demo.model.Booking;
import transport.app.demo.model.user.User;
import transport.app.demo.repository.BookingRepository;
import transport.app.demo.repository.UserRepository;
import transport.app.demo.security.JwtTokenProvider;
import transport.app.demo.util.EmailSender;

import javax.servlet.http.HttpServletRequest;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    private EmailSender emailSender;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          JwtTokenProvider jwtTokenProvider,
                          UserRepository userRepository,
                          EmailSender emailSender) {
        this.bookingRepository = bookingRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.emailSender = emailSender;
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    public void createBooking(Booking booking, HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        User user = userRepository.findByUsername(username);
        booking.setUser(user);
        bookingRepository.save(booking);

        String message =
                "Hey " + username + ",\n" +
                        "You just created an account with The Transport-App \n" +
                        "You are required to use the following link to verify your account\n" +
                        " –Please disregard if it wasn't you";

        emailSender.sendEmail(username, "Transport-App Booking service", message);
    }
}

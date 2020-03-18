package transport.app.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import transport.app.demo.model.Trip;
import transport.app.demo.model.user.User;
import transport.app.demo.payload.trip.NewTrip;
import transport.app.demo.repository.TripRepository;
import transport.app.demo.repository.UserRepository;
import transport.app.demo.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;

@Service
public class TripService {

    private JwtTokenProvider jwtTokenProvider;

    private TripRepository tripRepository;

    private UserRepository userRepository;

    @Autowired
    public TripService(JwtTokenProvider jwtTokenProvider, TripRepository tripRepository, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Trip createTrip(NewTrip newTrip, HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        User user = userRepository.findByUsername(username);

        Trip trip = new Trip();
        trip.setLeave(newTrip.getLeave());
        trip.setArrival(newTrip.getArrival());
        trip.setLeaveDate(newTrip.getLeaveDate());
        trip.setUser(user);
        return tripRepository.save(trip);
    }
}

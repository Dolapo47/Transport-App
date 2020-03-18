package transport.app.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import transport.app.demo.model.Bus;
import transport.app.demo.model.user.User;
import transport.app.demo.repository.BusRepository;
import transport.app.demo.repository.TripRepository;
import transport.app.demo.repository.UserRepository;
import transport.app.demo.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;

@Service
public class BusService {

    private JwtTokenProvider jwtTokenProvider;

    private BusRepository busRepository;

    private UserRepository userRepository;

    private TripRepository tripRepository;

    @Autowired
    public BusService(TripRepository tripRepository,JwtTokenProvider jwtTokenProvider, BusRepository busRepository, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.busRepository = busRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Bus createBus(Bus bus, HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        User user = userRepository.findByUsername(username);

        Bus newBus = new Bus();
        newBus.setBrand(bus.getBrand());
        newBus.setCapacity(bus.getCapacity());
        newBus.setPlateNo(bus.getPlateNo());
        newBus.setUser(user);
        return busRepository.save(newBus);
    }
}

package transport.app.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import transport.app.demo.exceptions.AppException;
import transport.app.demo.model.Bus;
import transport.app.demo.model.user.User;
import transport.app.demo.repository.BusRepository;
import transport.app.demo.repository.TripRepository;
import transport.app.demo.repository.UserRepository;
import transport.app.demo.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

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
    public void createBus(Bus bus, HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        User user = userRepository.findByUsername(username);

        Bus newBus = new Bus();
        newBus.setBrand(bus.getBrand());
        newBus.setCapacity(bus.getCapacity());
        newBus.setPlateNo(bus.getPlateNo());
        newBus.setUser(user);
        busRepository.save(newBus);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ArrayList<Bus> viewBus(){
        ArrayList<Bus> buses = new ArrayList<>();
        buses = (ArrayList<Bus>) busRepository.findAll();
        if(buses.size() < 1){
            throw new AppException("No bus found", HttpStatus.NOT_FOUND);
        }
        return buses;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBus(Long busId, HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        User user = userRepository.findByUsername(username);


        Optional<Bus> foundBus = busRepository.findById(busId);

        if(!foundBus.isPresent()){
            throw new AppException("Bus not found", HttpStatus.NOT_FOUND);
        }

        if(foundBus.get().getUser().getId() != user.getId()){
            throw new AppException("You cannot delete this bus", HttpStatus.BAD_REQUEST);
        }
        busRepository.delete(foundBus.get());
    }

    public ArrayList<Bus> viewAllAvailableBus(){
        ArrayList<Bus> availableBuses = busRepository.findBusesByOnTrip(false);
        if(availableBuses.size() < 1){
            throw new AppException("No bus found", HttpStatus.NOT_FOUND);
        }
        return availableBuses;
    }

    public ArrayList<Bus> viewAllUnavailableBus(){
        ArrayList<Bus> unAvailableBuses = busRepository.findBusesByOnTrip(true);
        if(unAvailableBuses.size() < 1){
            throw new AppException("No bus found", HttpStatus.NOT_FOUND);
        }
        return unAvailableBuses;
    }
}

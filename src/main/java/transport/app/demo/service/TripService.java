package transport.app.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import transport.app.demo.exceptions.AppException;
import transport.app.demo.model.Bus;
import transport.app.demo.model.Trip;
import transport.app.demo.model.user.User;
import transport.app.demo.payload.trip.NewTrip;
import transport.app.demo.repository.BusRepository;
import transport.app.demo.repository.TripRepository;
import transport.app.demo.repository.UserRepository;
import transport.app.demo.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    private JwtTokenProvider jwtTokenProvider;

    private TripRepository tripRepository;

    private UserRepository userRepository;

    private BusRepository busRepository;

    @Autowired
    public TripService(JwtTokenProvider jwtTokenProvider, TripRepository tripRepository, UserRepository userRepository,
                       BusRepository busRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.busRepository = busRepository;
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
        trip.setPrice(newTrip.getPrice());
        trip.setUser(user);

        return tripRepository.save(trip);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addBusToTrip(Long tripId, Long busId){
        Optional<Bus> foundBus = busRepository.findById(busId);
        Optional<Trip> foundTrip = tripRepository.findById(tripId);
        if(!foundTrip.isPresent()){
            throw new AppException("no trip matches the id", HttpStatus.NOT_FOUND);
        }

        if(!foundBus.isPresent()){
            throw new AppException("no bus matches the id", HttpStatus.NOT_FOUND);
        }

        if(foundBus.get().isOnTrip()){
            throw new AppException("bus is assigned to a trip", HttpStatus.BAD_REQUEST);
        }
        else{
            foundBus.get().setOnTrip(true);
            foundTrip.get().setBus(foundBus.get());
        }
        tripRepository.save(foundTrip.get());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeBusFromTrip(Long tripId, Long busId){
        Optional<Bus> foundBus = busRepository.findById(busId);
        Optional<Trip> foundTrip = tripRepository.findById(tripId);

        if(!foundTrip.isPresent()){
            throw new AppException("no trip matches the id", HttpStatus.NOT_FOUND);
        }

        if(!foundBus.isPresent()){
            throw new AppException("no bus matches the id", HttpStatus.NOT_FOUND);
        }
        if(!foundBus.get().isOnTrip()){
            throw new AppException("bus is not assigned to a trip", HttpStatus.BAD_REQUEST);
        }else{
            foundBus.get().setOnTrip(false);
            foundTrip.get().setBus(null);
        }
            tripRepository.save(foundTrip.get());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ArrayList<Trip> viewAllTrips(){
        ArrayList trips;
        trips = (ArrayList) tripRepository.findAll();
        if(trips.size() < 1){
            throw new AppException("No trip found", HttpStatus.NOT_FOUND);
        }
        return trips;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List viewAvailableTrips(){
    List completedTrips;
    completedTrips = tripRepository.findAllByComplete(true);
    if(completedTrips.size() < 1){
        throw new AppException("No available trips found", HttpStatus.NOT_FOUND);
    }
    return completedTrips;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List viewUnAvailableTrips(){
        List incompleteTrips;
        incompleteTrips = tripRepository.findAllByComplete(false);
        if(incompleteTrips.size() < 1){
            throw new AppException("No unavailable trips found", HttpStatus.NOT_FOUND);
        }
        return incompleteTrips;
    }
}

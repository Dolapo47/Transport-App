package transport.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transport.app.demo.model.Trip;
import transport.app.demo.payload.trip.NewTrip;
import transport.app.demo.responses.Response;
import transport.app.demo.service.BusService;
import transport.app.demo.service.TripService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/trip")
public class TripController {

    private TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/create-trip")
    public ResponseEntity<?>createTrip(@Valid @RequestBody NewTrip newTrip, HttpServletRequest request){
        Trip trip = tripService.createTrip(newTrip, request);
        Response<Trip> response = new Response<>(HttpStatus.CREATED);
        response.setMessage("Trip successfully created");
        response.setData(trip);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/view-all")
    public ResponseEntity<?>viewTrips(){
        ArrayList<Trip> trips = tripService.viewAllTrips();
        Response<ArrayList<Trip>> response = new Response<>(HttpStatus.OK);
        response.setMessage("Retrieved all trips");
        response.setData(trips);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/add/{tripId}/bus/{busId}")
    public ResponseEntity<?> addBusToTrip(@PathVariable Long tripId, @PathVariable Long busId, HttpServletRequest request){
        tripService.addBusToTrip(tripId, busId);
        Response<Trip> response = new Response<>(HttpStatus.CREATED);
        response.setMessage("Bus has been assigned to a trip");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/remove/{tripId}/bus/{busId}")
    public ResponseEntity<?> removeBusFromTrip(@PathVariable Long tripId, @PathVariable Long busId, HttpServletRequest request){
        tripService.removeBusFromTrip(tripId, busId);
        Response<Trip> response = new Response<>(HttpStatus.OK);
        response.setMessage("Bus has been removed from the trip");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

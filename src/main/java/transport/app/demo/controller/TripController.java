package transport.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transport.app.demo.model.Trip;
import transport.app.demo.payload.trip.NewTrip;
import transport.app.demo.responses.Response;
import transport.app.demo.service.TripService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
}

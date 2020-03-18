package transport.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transport.app.demo.model.Bus;
import transport.app.demo.model.Trip;
import transport.app.demo.payload.trip.NewTrip;
import transport.app.demo.repository.BusRepository;
import transport.app.demo.responses.Response;
import transport.app.demo.service.BusService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/bus")
public class BusController {
    private BusService busService;

    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping("/create-bus")
    public ResponseEntity<?> createBus(@Valid @RequestBody Bus bus, HttpServletRequest request){
        busService.createBus(bus, request);
        Response<Bus> response = new Response<>(HttpStatus.CREATED);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
}

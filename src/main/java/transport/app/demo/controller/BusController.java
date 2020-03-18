package transport.app.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transport.app.demo.model.Bus;
import transport.app.demo.responses.Response;
import transport.app.demo.service.BusService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/bus")
public class BusController {
    private BusService busService;

    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping("/bus")
    public ResponseEntity<?> createBus(@Valid @RequestBody Bus bus, HttpServletRequest request){
        busService.createBus(bus, request);
        Response<Bus> response = new Response<>(HttpStatus.CREATED);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping("/bus")
    public ResponseEntity<?> viewBus(){
        ArrayList buses = busService.viewBus();
        Response<ArrayList<Bus>> response = new Response<>(HttpStatus.OK);
        response.setMessage("Successfully retrieved all buses");
        response.setData(buses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/bus/{busId}")
    public ResponseEntity<?> deleteBus(@PathVariable Long busId, HttpServletRequest request){
        busService.deleteBus(busId, request);
        Response<ArrayList<Bus>> response = new Response<>(HttpStatus.OK);
        response.setMessage("Successfully deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

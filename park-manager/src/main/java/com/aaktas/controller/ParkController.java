package com.aaktas.controller;

import com.aaktas.model.Park;
import com.aaktas.model.Vehicle;
import com.aaktas.model.VehicleVO;
import com.aaktas.service.ParkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parks")
@Slf4j
public class ParkController {

    @Autowired
    private ParkService parkService;

    @GetMapping("/test")
    public ResponseEntity<String> test(){

        var responseHeaders = new HttpHeaders();
        responseHeaders.add("Responded", "Park Controller");

        return new ResponseEntity<String>("you reached the test method", responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<String> status(){

        var responseHeaders = new HttpHeaders();
        responseHeaders.add("Responded", "Park Controller");

        return new ResponseEntity<String>(parkService.status(), responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/{type}")
    public ResponseEntity<String> park(@PathVariable("type") Integer vehicleType, @RequestBody VehicleVO vehicleVO){

        var responseHeaders = new HttpHeaders();
        responseHeaders.add("Responded", "Park Controller");

        return new ResponseEntity<String>(parkService.park(vehicleVO, vehicleType), responseHeaders, HttpStatus.CREATED);

    }

    @GetMapping("/leave/{index}")
    public ResponseEntity<String> leave(@PathVariable("index") Integer parkIndex){

        var responseHeaders = new HttpHeaders();
        responseHeaders.add("Responded", "Park Controller");

        return new ResponseEntity<String>(parkService.leave(parkIndex), responseHeaders, HttpStatus.OK);
    }


}

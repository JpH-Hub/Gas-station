package com.example.Gas_station.controller

import com.example.Gas_station.exceptions.InvalidRequestException
import com.example.Gas_station.exceptions.PumpNotFound
import com.example.Gas_station.model.AddFuelInput
import com.example.Gas_station.model.FillUpInput
import com.example.Gas_station.model.FillUpLog
import com.example.Gas_station.model.FuelPump
import com.example.Gas_station.model.FuelRefillLog
import com.example.Gas_station.service.CustomerService
import com.example.Gas_station.service.LogService
import com.example.Gas_station.service.PumpService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pumps")
class GasStationController {
    private PumpService pumpService
    private CustomerService customerService
    private LogService logService


    GasStationController(PumpService pumpService, LogService logService, CustomerService customerService) {
        this.pumpService = pumpService
        this.logService = logService
        this.customerService = customerService
    }

    @GetMapping
    List<FuelPump> getAllPumps(@RequestParam(required = false, value = "type") String type) {
        return pumpService.getAllPumps(type)
    }

    @GetMapping("/{id}/log")
    List<FuelRefillLog> getPumpLogs(@PathVariable("id") Long id) {
        return logService.getAllPumpsLogs(id)
    }

    @GetMapping("/{id}/fill-up")
    List<FillUpLog> getCustomerLogs(@PathVariable("id") Long id) {
        return logService.getFillUpsByPumpId(id)
    }

    @PostMapping
    ResponseEntity createPump(@RequestBody FuelPump pump) {
        try {
            FuelPump createdPump = pumpService.createPump(pump)
            ResponseEntity.status(HttpStatus.CREATED).body(createdPump)
        } catch (InvalidRequestException ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body([erro: ex.message])
        }
    }


    @PostMapping("/{id}/fuel")
    ResponseEntity addFuel(@PathVariable("id") Long id, @RequestBody AddFuelInput input) {
        try {
            String message = pumpService.addFuelToPump(id, input)
            ResponseEntity.status(HttpStatus.OK).body([message: message])
        } catch (InvalidRequestException ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body([erro: ex.message])
        } catch (PumpNotFound ex) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body([erro: ex.message])
        }
    }

    @PostMapping("/{id}/fill-up")
    ResponseEntity fillUp(@PathVariable("id") Long id, @RequestBody FillUpInput input) {
        try {
            String message = customerService.fillUp(id, input)
            ResponseEntity.status(HttpStatus.OK).body([message: message])
        } catch (InvalidRequestException ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body([erro: ex.message])
        } catch (PumpNotFound ex) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body([erro: ex.message])
        }
    }

    @GetMapping("/{id}/profit")
    ResponseEntity pumpProfit(@PathVariable("id") Long id) {
        List<FuelRefillLog> fuelPumpsLogs = logService.getAllPumpsLogs(id)
        List<FillUpLog> fillUpLogs = logService.getFillUpsByPumpId(id)
        Double totalCost = fuelPumpsLogs.sum { it.totalCost }
        Double totalPaid = fillUpLogs.sum { it.totalPaid }
        Double profit = totalPaid - totalCost
        return ResponseEntity.status(HttpStatus.OK).body([profit: profit])
    }

}


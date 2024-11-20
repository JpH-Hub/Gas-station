package com.example.Gas_station.exceptions

class EmptyFuelPumpException extends RuntimeException {
    EmptyFuelPumpException(String message) {
        super(message)
    }
}

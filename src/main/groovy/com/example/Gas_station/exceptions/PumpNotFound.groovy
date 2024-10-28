package com.example.Gas_station.exceptions

class PumpNotFound extends RuntimeException {
    PumpNotFound(String message){
        super(message)
    }
}

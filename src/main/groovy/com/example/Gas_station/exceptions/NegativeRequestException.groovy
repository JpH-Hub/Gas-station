package com.example.Gas_station.exceptions

class NegativeRequestException extends RuntimeException {
    NegativeRequestException(String message) {
        super(message)
    }
}

package com.example.Gas_station.exceptions

class InvalidRequestException extends RuntimeException{
    InvalidRequestException(String message){
        super(message)
    }
}

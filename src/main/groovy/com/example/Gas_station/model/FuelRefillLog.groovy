package com.example.Gas_station.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "fuel_refill_logs")
class FuelRefillLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    Long pumpId
    Integer currentQuantity
    Integer addedQuantity
    Double totalCost
}

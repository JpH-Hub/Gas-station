package com.example.Gas_station.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "pumps")
class FuelPump {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String type
    Double sellingPrice
    Double purchasePrice
    Integer quantity = 0
}

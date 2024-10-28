package com.example.Gas_station.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name ="fill_up_logs")
class FillUpLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    Long selectedPumpId
    String name
    Integer amountRefueled
    Double totalPaid
}

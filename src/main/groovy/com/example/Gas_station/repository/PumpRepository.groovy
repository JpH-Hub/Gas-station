package com.example.Gas_station.repository

import com.example.Gas_station.model.FuelPump
import org.springframework.data.jpa.repository.JpaRepository


interface PumpRepository extends JpaRepository<FuelPump, Long> {
    List<FuelPump> findByType(String type)

    List<FuelPump> findAllByOrderByIdAsc()
}
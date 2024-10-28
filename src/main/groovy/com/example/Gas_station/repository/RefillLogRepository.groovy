package com.example.Gas_station.repository

import com.example.Gas_station.model.FuelRefillLog
import org.springframework.data.jpa.repository.JpaRepository


interface RefillLogRepository extends JpaRepository<FuelRefillLog, Long> {
    List<FuelRefillLog> findByPumpId(Long pumpId)

    List<FuelRefillLog> findAllByOrderByIdAsc()
}
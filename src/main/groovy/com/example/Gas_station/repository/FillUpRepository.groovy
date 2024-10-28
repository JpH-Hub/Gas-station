package com.example.Gas_station.repository


import com.example.Gas_station.model.FillUpLog

import org.springframework.data.jpa.repository.JpaRepository

interface FillUpRepository extends JpaRepository<FillUpLog, Long> {
    List<FillUpLog> findBySelectedPumpId(Long pumpId)

    List<FillUpLog> findAllByOrderByIdAsc()
}

package com.example.Gas_station.service

import com.example.Gas_station.model.FillUpInput
import com.example.Gas_station.model.FillUpLog
import com.example.Gas_station.model.FuelPump
import com.example.Gas_station.model.FuelRefillLog
import com.example.Gas_station.repository.FillUpRepository
import com.example.Gas_station.repository.RefillLogRepository
import org.springframework.stereotype.Service

@Service
class LogService {

    private RefillLogRepository refillLogRepository
    private FillUpRepository fillUpRepositoryRepository

    LogService(RefillLogRepository refillLogRepository, FillUpRepository customerRepository) {
        this.refillLogRepository = refillLogRepository
        this.fillUpRepositoryRepository = customerRepository
    }

    List<FillUpLog> getFillUpsByPumpId(Long pumpId) {
        return fillUpRepositoryRepository.findBySelectedPumpId(pumpId)
    }

    List<FuelRefillLog> getAllPumpsLogs(Long id) {
        return refillLogRepository.findByPumpId(id)
    }

}



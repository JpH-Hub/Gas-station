package com.example.Gas_station.service

import com.example.Gas_station.exceptions.InvalidRequestException
import com.example.Gas_station.exceptions.PumpNotFound
import com.example.Gas_station.model.AddFuelInput
import com.example.Gas_station.model.FuelPump
import com.example.Gas_station.model.FuelRefillLog
import com.example.Gas_station.model.PumpProfit
import com.example.Gas_station.repository.PumpRepository
import com.example.Gas_station.repository.RefillLogRepository
import org.springframework.stereotype.Service

@Service
class PumpService {


    private PumpRepository pumpRepository
    private RefillLogRepository refillLogRepository

    PumpService(PumpRepository pumpRepository, RefillLogRepository refillLogRepository) {
        this.pumpRepository = pumpRepository
        this.refillLogRepository = refillLogRepository
    }

    List<FuelPump> getAllPumps(String type) {
        if (type) {
            return pumpRepository.findByType(type)
        }
        return pumpRepository.findAllByOrderByIdAsc()
    }

    FuelPump createPump(FuelPump pump) {
        if (!pump.type || !pump.purchasePrice || !pump.sellingPrice) {
            throw new InvalidRequestException("type and selling and purchace price are required.")
        }
        return pumpRepository.save(pump)
    }

    String addFuelToPump(Long id, AddFuelInput input) {
        Optional<FuelPump> optionalPump = pumpRepository.findById(id)
        FuelPump pump = optionalPump.orElseThrow { new PumpNotFound("Pump ${id} not found") }
        if (input.quantity <= 0) {
            throw new InvalidRequestException("Quantidade de combustivel inválida, o numero tem ser positivo")
        } else if (input.quantity >= 10000) {
            throw new InvalidRequestException("Quantidade maior do que as bombas de combustivel podem conter")
        } else if (pump == null) {
            throw new InvalidRequestException("Essa bomba de combustivel não existe, insira um id válido")
        } else if (pump.quantity >= 10000 || pump.quantity + input.quantity >= 10000) {
            throw new InvalidRequestException("A bomba de combustivel não suporta mais")
        }
        Integer currentQuantity = pump.quantity
        pump.quantity += input.quantity
        pumpRepository.save(pump)
        FuelRefillLog log = new FuelRefillLog(
                pumpId: pump.id,
                currentQuantity: currentQuantity,
                addedQuantity: input.quantity,
                totalCost: input.quantity * pump.purchasePrice
        )
        refillLogRepository.save(log)
        return "Fuel added sucessfully a pump ${id}"
    }

    PumpProfit calculateTheProfit() {

    }


}


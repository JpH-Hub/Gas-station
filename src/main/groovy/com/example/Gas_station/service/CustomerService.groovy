package com.example.Gas_station.service

import com.example.Gas_station.exceptions.InvalidRequestException
import com.example.Gas_station.exceptions.PumpNotFound
import com.example.Gas_station.model.FillUpInput
import com.example.Gas_station.model.FillUpLog
import com.example.Gas_station.model.FuelPump
import com.example.Gas_station.repository.FillUpRepository
import com.example.Gas_station.repository.PumpRepository
import org.springframework.stereotype.Service

@Service
class CustomerService {

    private FillUpRepository customerRepository
    private PumpRepository pumpRepository

    CustomerService(FillUpRepository customerRepository, PumpRepository pumpRepository) {
        this.customerRepository = customerRepository
        this.pumpRepository = pumpRepository
    }

    String fillUp(Long pumpId, FillUpInput input) {
        Optional<FuelPump> optionalPump = pumpRepository.findById(pumpId)
        FuelPump pump = optionalPump.orElseThrow { new PumpNotFound("Pump ${pumpId} not found") }

        if (input.amountRefueled <= 0) {
            throw new InvalidRequestException("Insira um valor válido")
        } else if (input.amountRefueled > pump.quantity) {
            throw new InvalidRequestException("A bomba de combustivel não possui essa quantidade de combustivel")
        } else if (pump.quantity <= 0) {
            throw new InvalidRequestException("A bomba de combustivel está vazia")
        }

        FillUpLog log = new FillUpLog(
                name: input.customerName,
                selectedPumpId: pumpId,
                amountRefueled: input.amountRefueled,
                totalPaid: pump.sellingPrice * input.amountRefueled
        )
        pump.quantity -= input.amountRefueled
        customerRepository.save(log)

        return "O cliente abasteceu com sucesso"
    }
}

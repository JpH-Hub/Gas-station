package com.example.Gas_station.service

import com.example.Gas_station.exceptions.EmptyFuelPumpException
import com.example.Gas_station.exceptions.InvalidRequestException
import com.example.Gas_station.exceptions.NegativeRequestException
import com.example.Gas_station.exceptions.PumpNotFound
import com.example.Gas_station.model.FillUpInput
import com.example.Gas_station.model.FuelPump
import com.example.Gas_station.repository.FillUpRepository
import com.example.Gas_station.repository.PumpRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito


import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.mockito.Mockito.when


class CustomerServiceTest {
    private PumpRepository pumpRepository = Mockito.mock(PumpRepository.class)
    private FillUpRepository fillUpRepository = Mockito.mock(FillUpRepository.class)


    private CustomerService customerService = new CustomerService(fillUpRepository, pumpRepository,)


    @Test
    void aQuantidadeFoiDiminuidaComSucesso() {

        FuelPump pump = new FuelPump(quantity: 1000.0, sellingPrice: 5.0)
        FillUpInput input = new FillUpInput(amountRefueled: 500.0)


        when(pumpRepository.findById(1L)).thenReturn(Optional.of(pump))


        String result = customerService.fillUp(1L, input)


        assertEquals("O cliente abasteceu com sucesso", result)
    }


    @Test
    void deveEnviarExcessaoQuandoNaoAcharAPump() {

        when(pumpRepository.findById(1L)).thenReturn(Optional.empty())

        FillUpInput fill = new FillUpInput(customerName: "joao", amountRefueled: 500)

        PumpNotFound pumpNotFound = assertThrows(PumpNotFound.class, () ->{
            customerService.fillUp(1L, fill)
        })

        assertEquals("Pump 1 not found", pumpNotFound.message)
    }
    
    @Test
    void deveEnviarExcessaoQuandoValorForNegativo(){
        FuelPump pump = new FuelPump(quantity: 1000.0, sellingPrice: 5.0)
        FillUpInput fill = new FillUpInput(amountRefueled: -500.0)


        when(pumpRepository.findById(1L)).thenReturn(Optional.of(pump))

        NegativeRequestException negativeFill=  assertThrows(NegativeRequestException.class, () -> {
            customerService.fillUp(1L, fill)
        })

        assertEquals("Insira um valor válido", negativeFill.message)
    }

    @Test
    void deveEnviarExcessaoQuandoPumpEstiverVazia(){
        FuelPump pump = new FuelPump(quantity: 0, sellingPrice: 5.0)
        FillUpInput fill = new FillUpInput(amountRefueled: 500.0)


        when(pumpRepository.findById(1L)).thenReturn(Optional.of(pump))

        EmptyFuelPumpException emptyFuelPump =  assertThrows(EmptyFuelPumpException.class, () -> {
            customerService.fillUp(1L, fill)
        })

        assertEquals("A bomba de combustivel está vazia", emptyFuelPump.message)
    }


}

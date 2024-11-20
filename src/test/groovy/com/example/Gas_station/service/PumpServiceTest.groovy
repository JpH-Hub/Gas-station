package com.example.Gas_station.service

import com.example.Gas_station.model.AddFuelInput
import com.example.Gas_station.model.FillUpLog
import com.example.Gas_station.model.FuelPump
import com.example.Gas_station.model.FuelRefillLog
import com.example.Gas_station.model.PumpProfit
import com.example.Gas_station.repository.PumpRepository
import com.example.Gas_station.repository.RefillLogRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito


import static org.junit.jupiter.api.Assertions.assertEquals
import static org.mockito.Mockito.when

class PumpServiceTest {

    private PumpRepository pumpRepository = Mockito.mock(PumpRepository.class)

    private LogService logService = Mockito.mock(LogService.class)

    private RefillLogRepository refillLogRepository = Mockito.mock(RefillLogRepository.class)

    private PumpService pumpService = new PumpService(pumpRepository, refillLogRepository, logService)


    @Test
    void deveCalcularOProfitcomSucesso() {
        //given ou setUp
        FuelRefillLog refillLog = new FuelRefillLog();
        refillLog.setId(1L);
        refillLog.setPumpId(1L);
        refillLog.setCurrentQuantity(100);
        refillLog.setAddedQuantity(500);
        refillLog.setTotalCost(1000.0);

        FillUpLog fillUpLog = new FillUpLog();
        fillUpLog.setId(1L);
        fillUpLog.setSelectedPumpId(1L);
        fillUpLog.setName("joao");
        fillUpLog.setAmountRefueled(500);
        fillUpLog.setTotalPaid(2000.0);

        List<FuelRefillLog> list = [refillLog]
        List<FillUpLog> fillUpLogs = [fillUpLog]

        when(logService.getAllPumpsLogs(1L)).thenReturn(list);
        when(logService.getFillUpsByPumpId(1L)).thenReturn(fillUpLogs);

        //when
        PumpProfit profit = pumpService.calculateTheProfit(1L)

        //then
        assertEquals(1000, profit.totalProfit)


    }


    @Test
    void deveAddCombustivelComSucesso() {
        //setup
        FuelPump pump = new FuelPump(id: 1, quantity: 5000, purchasePrice: 5.0)
        AddFuelInput input = new AddFuelInput(quantity: 1000)

        when(pumpRepository.findById(1L)).thenReturn(Optional.of(pump))


        //when
        String result = pumpService.addFuelToPump(1L, input)

        //then
        assertEquals("Fuel added sucessfully a pump 1", result)
    }

}
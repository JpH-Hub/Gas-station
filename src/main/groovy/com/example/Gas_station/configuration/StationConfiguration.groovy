package com.example.Gas_station.configuration

import com.example.Gas_station.controller.GasStationController
import com.example.Gas_station.repository.FillUpRepository
import com.example.Gas_station.repository.PumpRepository
import com.example.Gas_station.repository.RefillLogRepository
import com.example.Gas_station.service.CustomerService
import com.example.Gas_station.service.LogService
import com.example.Gas_station.service.PumpService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories("com.example.Gas_station.repository")
@Configuration
@ComponentScan(basePackages = "com.example")
class StationConfiguration {

    @Bean
    GasStationController gasStationController(PumpService pumpService, LogService logService, CustomerService customerService) {
        return new GasStationController(pumpService, logService, customerService)
    }

    @Bean
    PumpService pumpService(PumpRepository pumpRepository, RefillLogRepository refillLogRepository) {
        return new PumpService(pumpRepository, refillLogRepository)
    }

    @Bean
    LogService logService(RefillLogRepository refillLogRepository, FillUpRepository customerRepository){
        return new LogService(refillLogRepository, customerRepository)
    }

    @Bean
    CustomerService customerService(FillUpRepository customerRepository, PumpRepository pumpRepository){
        return new CustomerService(customerRepository, pumpRepository)
    }
}

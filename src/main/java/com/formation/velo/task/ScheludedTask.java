package com.formation.velo.task;

import com.formation.velo.service.StationService;
import com.formation.velo.service.ParkingService;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log
public class ScheludedTask {

    private final StationService stationService;
    private final ParkingService parkingService;
    public ScheludedTask(StationService stationService, ParkingService parkingService) {
        this.stationService = stationService;
        this.parkingService = parkingService;
    }

    @Scheduled(fixedRate = 60000)
    public void searchNewMatchStation(){
        log.info("updating...");

        stationService.getRecords();
        parkingService.getDatas();

        /* try {
            stationService.getRecords();
            log.info("✅ station update");
        } catch (Exception e) {
            log.info("❌ station not update" + e.getMessage());
        }
        try {
            parkingService.getDatas();
            log.info("✅ parking update");
        } catch (Exception e) {
            log.info("❌ parking not update" + e.getMessage());
        } */
    }

}

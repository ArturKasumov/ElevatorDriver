package com.sytoss.edu2021.contollers;

import com.sytoss.edu2021.repo.dto.CabinBOM;
import com.sytoss.edu2021.repo.dto.Floor;
import com.sytoss.edu2021.services.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
public class Shedule {
    private int buildingId;
    private int cabinNumber;
    private int endFloor;
    @Autowired
    private FloorService floorService;

    public Shedule(int buildingId, int cabinNumber, int endFloor,FloorService floorService) {
        this.buildingId = buildingId;
        this.cabinNumber = cabinNumber;
        this.endFloor = endFloor;
        this.floorService = floorService;
    }

    @Scheduled(fixedRate = 10000)
    public CabinBOM execute(){
        return floorService.goToFloor(buildingId,cabinNumber,endFloor);
    }
}

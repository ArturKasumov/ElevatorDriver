package com.sytoss.edu2021.services;

import com.sytoss.edu2021.contollers.FeignProxyAdmin;
import com.sytoss.edu2021.contollers.FeignProxyCabin;
import com.sytoss.edu2021.contollers.FeignProxyEngine;
import com.sytoss.edu2021.contollers.Shedule;
import com.sytoss.edu2021.repo.dto.CabinBOM;
import com.sytoss.edu2021.repo.dto.EngineBOM;
import com.sytoss.edu2021.repo.dto.Floor;
import com.sytoss.edu2021.repo.dto.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class FloorService {
    @Autowired
    private FeignProxyAdmin proxyAdmin;

    @Autowired
    private FeignProxyEngine proxyEngine;

    @Autowired
    private FeignProxyCabin proxyCabin;


    public CabinBOM goToFloor(int buildingId, int cabinNumber, int endFloor) {
        CabinBOM cabin;
        try {
            cabin = proxyAdmin.getCabinByIdBuilding(buildingId, cabinNumber);
        } catch (HttpStatusCodeException e) {
            throw new EntityNotFoundException("There is no such cabin");
        }

        EngineBOM engine = proxyEngine.getEngine(cabin.getId());

        ArrayList<Floor> floors = new ArrayList<>();
        for (int i = 1; i <= cabin.getFloorButtons().length; ++i) {
            floors.add(new Floor(i, cabin));
        }

        engine.setListOfFloors(floors);

        cabin.setEngine(engine);
        Route route = new Route();
        route.addRoutFloor(engine.getCurrentFloor(), endFloor);
        cabin.getEngine().setRoute(route);

        while (cabin.getEngine().getCurrentFloor() != endFloor) {
            cabin.startMovement();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            proxyEngine.goToFloor(engine);
            //cabin = proxyCabin.goToFloor(buildingId,cabinNumber,endFloor);
        }
        cabin.getEngine().getListOfFloors().get(endFloor - 1).setHasCabinOnFloor(true);
        return cabin;
    }
}

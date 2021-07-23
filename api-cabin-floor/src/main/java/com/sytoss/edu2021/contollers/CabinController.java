package com.sytoss.edu2021.contollers;

import com.sytoss.edu2021.bom.CabinBOM;
import com.sytoss.edu2021.services.CabinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cabin_floor/cabin")
public class CabinController {

    @Autowired
    private CabinService cabinService;

    @GetMapping("/send/message/{idCabin}")
    public String sendMessage(@PathVariable int idCabin) {
        return cabinService.sendMessage(idCabin);
    }

    @GetMapping("/add/floor/{buildingId}/{cabinNumber}/{floor}")
    public void addFloorToRoute(@PathVariable int buildingId, @PathVariable int cabinNumber, @PathVariable int floor) {
        cabinService.addFloorToRoute(buildingId, cabinNumber, floor);
    }

    @PostMapping("/start/{buildingId}/{cabinNumber}")
    public void startMovement(@PathVariable int buildingId, @PathVariable int cabinNumber) {
        cabinService.startMovement(buildingId, cabinNumber);
    }

    @GetMapping("/get/info/{buildingId}/{cabinNumber}")
    public CabinBOM getCabinInfo(@PathVariable int buildingId, @PathVariable int cabinNumber) {
        return cabinService.getCabinInfo(buildingId, cabinNumber);
    }

}


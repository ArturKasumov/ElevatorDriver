package com.sytoss.edu2021.contollers;
import com.sytoss.edu2021.repo.dto.CabinBOM;
import com.sytoss.edu2021.services.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/cabin_floor/floor")
public class FloorController {
    @Autowired
    private FloorService floorService;

    @GetMapping("/gotofloor/{buildingId}/{cabinNumber}/{toFloor}")
    public CabinBOM goToFloor(@PathVariable int buildingId, @PathVariable int cabinNumber, @PathVariable int toFloor) {
        //Shedule shedule = new Shedule(buildingId,cabinNumber,toFloor,floorService);
        //return shedule.execute();
        return floorService.goToFloor(buildingId,cabinNumber,toFloor);
    }
}


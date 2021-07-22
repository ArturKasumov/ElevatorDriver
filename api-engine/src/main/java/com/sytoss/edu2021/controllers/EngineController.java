package com.sytoss.edu2021.controllers;

import com.sytoss.edu2021.repo.dto.CabinBOM;
import com.sytoss.edu2021.repo.dto.EngineBOM;
import com.sytoss.edu2021.services.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/engine")
public class EngineController {

    @Autowired
    private EngineService engineService;

    @PostMapping("/")
    public EngineBOM create(@RequestBody CabinBOM cabin){
        return engineService.create(cabin);
    }

    @PostMapping ("/engines/")
    public EngineBOM[] getEngines(@RequestBody Integer[] ids){
        return engineService.getEngines(ids);
    }

    @GetMapping ("/get/{idCabin}")
    public EngineBOM getEngine(@PathVariable Integer idCabin){
        return engineService.getEngine(idCabin);
    }

    @PostMapping("/goToFloor")
    public void goToFloor(@RequestBody EngineBOM engine){
        engineService.update(engine);
    }

}

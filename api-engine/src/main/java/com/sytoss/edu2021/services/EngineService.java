package com.sytoss.edu2021.services;

import com.sytoss.edu2021.common.Direction;
import com.sytoss.edu2021.repo.EngineRepository;
import com.sytoss.edu2021.bom.EngineBOM;
import com.sytoss.edu2021.services.convertor.EngineConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EngineService {

    @Autowired
    EngineRepository repository;

    public EngineBOM create(Integer idCabin) {

        EngineBOM engineBOM = new EngineBOM(idCabin);
        EngineDTO engineDTO = new EngineDTO();

        new EngineConvertor().toDTO(engineBOM, engineDTO);
        engineDTO = repository.save(engineDTO);
        new EngineConvertor().fromDTO(engineDTO, engineBOM);
        return engineBOM;
    }

    public EngineBOM[] getEngines(Integer[] ids) {
        EngineDTO[] engineDTOS = new EngineDTO[ids.length];
        EngineBOM[] engineBOMS = new EngineBOM[engineDTOS.length];
        EngineConvertor engineConvertor = new EngineConvertor();
        for (int i = 0; i < ids.length; i++) {
            engineDTOS[i] = repository.findEngineDTOById(ids[i]);
            engineBOMS[i] = new EngineBOM();
            engineConvertor.fromDTO(engineDTOS[i], engineBOMS[i]);
        }

        return engineBOMS;
    }

    public EngineBOM getEngine(Integer idCabin) {
        EngineDTO engineDTO = repository.findEngineDTById(idCabin);

        if (engineDTO == null) {
            throw new EntityNotFoundException("There is no such engine.");
        }

        EngineBOM engineBOM = new EngineBOM();
        new EngineConvertor().fromDTO(engineDTO, engineBOM);
        return engineBOM;
    }

    public void update(EngineBOM engine) {
        EngineDTO engineDTO = new EngineDTO();
        new EngineConvertor().toDTO(engine,engineDTO);
        engineDTO = repository.save(engineDTO);
    }

    public EngineBOM goToFloor(Integer cabinId) {
        EngineDTO engineDTO = repository.findEngineDTById(cabinId);
        EngineBOM engine = new EngineBOM();
        new EngineConvertor().fromDTO(engineDTO, engine);

        int endFloor = engine.getCurrentFloor();

        if(engine.getRoute().getDirection().equals(Direction.UP))
            endFloor = engine.getRoute().getMaxValue();
        if(engine.getRoute().getDirection().equals(Direction.DOWN))
            endFloor = engine.getRoute().getMinValue();

        while (engine.getCurrentFloor() != endFloor) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            engine.start();
        }

        return engine;
    }
}

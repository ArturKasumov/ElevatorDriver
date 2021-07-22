package com.sytoss.edu2021.services;

import com.sytoss.edu2021.bom.EngineStatus;
import com.sytoss.edu2021.controllers.FeignProxyAdmin;
import com.sytoss.edu2021.repo.EngineRepository;
import com.sytoss.edu2021.repo.dto.CabinBOM;
import com.sytoss.edu2021.repo.dto.EngineBOM;
import com.sytoss.edu2021.repo.dto.EngineDTO;
import com.sytoss.edu2021.services.convertor.EngineConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EngineService {

    @Autowired
    private EngineRepository repository;

    @Autowired
    private FeignProxyAdmin proxyAdmin;

    public EngineBOM create(CabinBOM cabin) {

        EngineBOM engineBOM = new EngineBOM(cabin.getId());
        EngineDTO engineDTO = new EngineDTO();
        //CabinBOM cabinBOM = proxyAdmin.getCabinById(cabin.getId());
        engineBOM.setCabin(cabin);
        engineBOM.setBuilding(cabin.getBuilding());

        new EngineConvertor().toDTO(engineBOM, engineDTO);
        engineDTO.setStatus(EngineStatus.STOP);
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
}

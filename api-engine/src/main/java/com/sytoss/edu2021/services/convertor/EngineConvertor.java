package com.sytoss.edu2021.services.convertor;

import com.sytoss.edu2021.controllers.FeignProxyAdmin;
import com.sytoss.edu2021.repo.dto.BuildingBOM;
import com.sytoss.edu2021.repo.dto.CabinBOM;
import com.sytoss.edu2021.repo.dto.EngineBOM;
import com.sytoss.edu2021.repo.dto.EngineDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class EngineConvertor {
    public void fromDTO(EngineDTO source, EngineBOM destination)
    {
        destination.setId(source.getId());
        destination.setCurrentFloor(source.getCurrentFloor());
        destination.setCabin(new CabinBOM(source.getIdCabin()));
        destination.setBuilding(new BuildingBOM(source.getIdBuild()));
        destination.setStatus(source.getStatus());
    }

    public void toDTO(EngineBOM source, EngineDTO destination)
    {
        if(source.getId() != null)
        {
            destination.setId(source.getId());
        }
        destination.setCurrentFloor(source.getCurrentFloor());
        destination.setIdCabin(source.getCabin().getId());
        destination.setIdBuild(source.getBuilding().getId());
        destination.setStatus(source.getStatus());
    }
}

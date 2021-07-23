package com.sytoss.edu2021.services.convertor;

import com.sytoss.edu2021.bom.EngineBOM;

public class EngineConvertor {
    public void fromDTO(EngineDTO source, EngineBOM destination)
    {
        destination.setId(source.getId());
        destination.setCurrentFloor(source.getCurrentFloor());
    }

    public void toDTO(EngineBOM source, EngineDTO destination)
    {
        if(source.getId() != null)
        {
            destination.setId(source.getId());
        }
        destination.setCurrentFloor(source.getCurrentFloor());
    }
}

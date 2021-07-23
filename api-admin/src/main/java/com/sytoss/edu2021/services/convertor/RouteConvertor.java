package com.sytoss.edu2021.services.convertor;

import com.sytoss.edu2021.bom.BuildingBOM;
import com.sytoss.edu2021.bom.EngineBOM;
import com.sytoss.edu2021.common.RouteBOM;
import com.sytoss.edu2021.repo.dto.BuildingDTO;
import com.sytoss.edu2021.repo.dto.EngineDTO;
import com.sytoss.edu2021.repo.dto.RouteDTO;

import java.util.Set;
import java.util.TreeSet;

public class RouteConvertor {
    public void toDTO(RouteBOM source, RouteDTO destination) {

    }

    public void fromDTO(RouteDTO source, RouteBOM destination) {
       /* Set<Integer> set = new TreeSet<>();
        set.add(source.getFloorNumber());
        destination.setQueueOfFloors(set);*/
    }
}

package com.sytoss.edu2021.services;

import com.sytoss.edu2021.controllers.FeignProxyEngine;
import com.sytoss.edu2021.repo.BuildingRepository;
import com.sytoss.edu2021.repo.CabinRepository;
import com.sytoss.edu2021.repo.dto.*;
import com.sytoss.edu2021.services.convertor.BuildingConvertor;
import com.sytoss.edu2021.services.convertor.CabinConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private CabinRepository cabinRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private FeignProxyEngine proxy;

    public BuildingBOM getBuildingById(int id) {
        BuildingDTO dto = buildingRepository.findBuildingById(id);
        if (dto == null) {
            throw new EntityNotFoundException(id, "There is no such building " + id);
        }
        BuildingBOM result = new BuildingBOM();
        new BuildingConvertor().fromDTO(dto, result);
        return result;
    }

    public BuildingBOM register(BuildingBOM building) {
        if (building.isValid()) {
            try {
                BuildingBOM checkBuilding = searchByAddress(building.getAddress());
                throw new AlreadyExistsException("Building with this address already registered. BuildingId=" + checkBuilding.getId());
            } catch (EntityNotFoundException e) {
                BuildingDTO dto = new BuildingDTO();
                new BuildingConvertor().toDTO(building, dto);
                dto = buildingRepository.save(dto);
                new BuildingConvertor().fromDTO(dto, building);
                return building;
            }
        } else {
            throw new ValidationException("Data is not valid");
        }
    }


    public BuildingBOM addCabin(int buildingId, CabinBOM cabin) {
        if (cabin.isValid()) {
            BuildingBOM building = getBuildingById(buildingId);
            if (building.findCabinByNumber(cabin.getNumber()) != null) {
                throw new ValidationException("Building id = " + buildingId + " already contains cabin with number = " + cabin.getNumber());
            } else {
                Integer[] cabinIds = building.getCabinIdList();

                EngineBOM[] engines = proxy.getEngines(cabinIds);

                for (int i = 0; i < building.getCabins().size(); i++) {
                    building.getCabins().get(i).setEngine(engines[i]);
                }
                CabinDTO cabinDTO = new CabinDTO();
                new CabinConvertor().toDTO(cabin, cabinDTO);
                new CabinConvertor().toDTO(building, cabinDTO);
                cabinRepository.save(cabinDTO);
                building.addCabin(cabin);
                new CabinConvertor().fromDTO(cabinDTO,cabin);
                cabin.setBuilding(building);

                EngineBOM engineBOM = proxy.create(cabin);
                cabin.setEngine(engineBOM);
                return building;
            }
        } else {
            throw new ValidationException("Invalid cabin number (number should be > 0)");
        }
    }


    public BuildingBOM searchByAddress(String address) {
        BuildingDTO building = buildingRepository.findBuildingByAddress(address);
        if (building != null) {
            BuildingBOM buildingBOM = new BuildingBOM();
            new BuildingConvertor().fromDTO(building, buildingBOM);
            return buildingBOM;
        } else {
            throw new EntityNotFoundException("There is no building on this address: " + address);
        }
    }

    public BuildingBOM searchById(Integer id) {
        BuildingDTO building = buildingRepository.findBuildingById(id);
        if (building != null) {
            BuildingBOM buildingBOM = new BuildingBOM();
            new BuildingConvertor().fromDTO(building, buildingBOM);
            return buildingBOM;
        } else {
            throw new EntityNotFoundException("There is no building on this id: " + id);
        }
    }
}
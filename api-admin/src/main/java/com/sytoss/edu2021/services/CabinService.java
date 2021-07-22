package com.sytoss.edu2021.services;

import com.sytoss.edu2021.repo.BuildingRepository;
import com.sytoss.edu2021.repo.CabinRepository;
import com.sytoss.edu2021.repo.LogRepository;
import com.sytoss.edu2021.repo.dto.*;
import com.sytoss.edu2021.services.convertor.BuildingConvertor;
import com.sytoss.edu2021.services.convertor.CabinConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CabinService {
    @Autowired
    private CabinRepository cabinRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private RestTemplate restTemplate;

    public CabinBOM getCabin(String addressOfBuilding, Integer numberOfCabin) {
        BuildingDTO buildingDTO = buildingRepository.findBuildingByAddress(addressOfBuilding);
        CabinDTO cabinDTO = cabinRepository.findCabinByBuilding_IdAndAndNumber(buildingDTO.getId(), numberOfCabin);
        if (cabinDTO == null) {
            throw new EntityNotFoundException("There is no such cabin");
        }
        CabinBOM cabinBOM = new CabinBOM();
        new CabinConvertor().fromDTO(cabinDTO, cabinBOM);

        BuildingBOM buildingBOM = new BuildingBOM();
        new BuildingConvertor().fromDTO(buildingDTO, buildingBOM);

        cabinBOM.setBuilding(buildingBOM);
        Integer[] buttons = new Integer[buildingDTO.getFloorsAmount()];
        for (int i = 0; i < buttons.length; ++i) {
            buttons[i] = i + 1;
        }

        cabinBOM.setFloorButtons(buttons);
        return cabinBOM;
    }

    public CabinBOM getCabinByIdBuilding(int buildingId, int numberOfCabin) {
        BuildingDTO buildingDTO = buildingRepository.findBuildingById(buildingId);
        CabinDTO cabinDTO = cabinRepository.findCabinByBuilding_IdAndAndNumber(buildingDTO.getId(), numberOfCabin);
        if (cabinDTO == null) {
            throw new EntityNotFoundException("There is no such cabin");
        }
        CabinBOM cabinBOM = new CabinBOM();
        new CabinConvertor().fromDTO(cabinDTO, cabinBOM);

        BuildingBOM buildingBOM = new BuildingBOM();
        new BuildingConvertor().fromDTO(buildingDTO, buildingBOM);

        cabinBOM.setBuilding(buildingBOM);
        Integer[] buttons = new Integer[buildingDTO.getFloorsAmount()];
        for (int i = 0; i < buttons.length; ++i) {
            buttons[i] = i + 1;
        }

        cabinBOM.setFloorButtons(buttons);
        return cabinBOM;
    }

    public String getMessageAboutEmergencyInCabin(Integer idCabin) {
        String message = restTemplate.getForEntity("http://localhost:6070/api/cabin_floor/cabin/send/message/{idCabin}", String.class, idCabin).getBody();
        logRepository.save(new LogDTO(message, LocalDateTime.now()));
        return message;
    }

    public CabinBOM getCabinById(Integer idCabin) {
        CabinDTO cabinDTO = cabinRepository.findCabinDTOById(idCabin);
        if (cabinDTO == null) {
            throw new EntityNotFoundException("There is no such cabin");
        } else {
            CabinBOM cabinBOM = new CabinBOM();
            new CabinConvertor().fromDTO(cabinDTO,cabinBOM);
            BuildingBOM building = new BuildingBOM();
            new  BuildingConvertor().fromDTO(cabinDTO.getBuilding(),building);
            cabinBOM.setBuilding(building);
            return cabinBOM;
        }
    }
}
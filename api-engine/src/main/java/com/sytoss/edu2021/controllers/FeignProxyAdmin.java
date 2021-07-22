package com.sytoss.edu2021.controllers;

import com.sytoss.edu2021.repo.dto.BuildingBOM;
import com.sytoss.edu2021.repo.dto.CabinBOM;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="api-admin", url="localhost:6060/api/building")
public interface FeignProxyAdmin {
    @GetMapping("/get/cabin/id/{idCabin}")
    CabinBOM getCabinById(@PathVariable Integer idCabin);
    @GetMapping("/find/id/{id}")
    BuildingBOM searchBuildingById(@PathVariable Integer id);
}

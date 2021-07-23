package com.sytoss.edu2021.repo;

import com.sytoss.edu2021.repo.dto.EngineDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineRepository extends CrudRepository<EngineDTO,Integer> {
  
    EngineDTO findEngineDTOByBuildingIdAndCabinNumber(Integer buildingId,Integer CabinNumber);
}

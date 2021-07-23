package com.sytoss.edu2021.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineRepository extends JpaRepository<EngineDTO,Integer> {
    EngineDTO findEngineDTOById(int id);
    //EngineDTO[] findEngineDTOSById(Integer[] ids);
    EngineDTO findEngineDTById(Integer id);

}

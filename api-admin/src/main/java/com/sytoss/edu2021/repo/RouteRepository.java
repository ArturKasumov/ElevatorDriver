package com.sytoss.edu2021.repo;

import com.sytoss.edu2021.repo.dto.RouteDTO;
import com.sytoss.edu2021.repo.dto.RouteDTOId;
import org.springframework.data.repository.CrudRepository;

public interface RouteRepository extends CrudRepository<RouteDTO, RouteDTOId> {
    RouteDTO findRouteDTOByRouteDTOId(RouteDTOId routeDTOId);
}

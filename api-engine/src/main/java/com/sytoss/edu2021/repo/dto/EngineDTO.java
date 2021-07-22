package com.sytoss.edu2021.repo.dto;

import com.sytoss.edu2021.bom.EngineStatus;
import com.sytoss.edu2021.repo.EngineRepository;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity(name = "app_engine")
public class EngineDTO {

    @Id
    @Column(name = "id_engine")
    private Integer id;

    @Column(name="id_build")
    private int idBuild;

    @Column(name="id_cabin")
    private int idCabin;

    @Column(name="current_floor")
    private int currentFloor;

    @Column(name="status")
    private EngineStatus status;




}

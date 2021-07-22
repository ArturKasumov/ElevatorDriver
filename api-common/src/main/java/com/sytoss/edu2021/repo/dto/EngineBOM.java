package com.sytoss.edu2021.repo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sytoss.edu2021.bom.EngineStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class EngineBOM {
    @JsonIgnore
    private BuildingBOM building;
    @JsonIgnore
    private CabinBOM cabin;

    private EngineStatus status;
    private boolean isMoving;
    @JsonIgnore
    private Route route;
    @JsonIgnore
    private ArrayList<Floor> listOfFloors = new ArrayList<>();
    private boolean isEmergencyStop;

    private Integer id;

    private Integer currentFloor = 1;

    public EngineBOM() {
        listOfFloors = new ArrayList<>();
        route = new Route();
    }

    public EngineBOM(int id) {
        this.id = id;
        listOfFloors = new ArrayList<>();
        route = new Route();

    }

    public EngineBOM(Route route, ArrayList<Floor> listOfFloors, Integer currentFloor) {
        this.route = route;
        this.listOfFloors = listOfFloors;
        this.currentFloor = currentFloor;

    }

    public void move() {
        if (route.getDirection().equals(Direction.UP)) {
            if (currentFloor + 1 <= route.getMaxValue()) {
                currentFloor++;
                if (route.getQueueOfFloors().contains(currentFloor)) {
                    stop();
                    route.getQueueOfFloors().remove(currentFloor);
                }
            }
        }

        if (route.getDirection().equals(Direction.DOWN)) {
            if (currentFloor - 1 >= route.getMinValue()) {
                currentFloor--;
                if (route.getQueueOfFloors().contains(currentFloor)) {
                    stop();
                    route.getQueueOfFloors().remove(currentFloor);
                }
            }
        }

        if(route.getQueueOfFloors().isEmpty()) {
            route.setDirection(Direction.STABLE);
        }
    }

    public void start() {
        isMoving = true;
        isEmergencyStop = false;
        move();
    }

    public void stop() {
        isMoving = false;
    }

    private void emergencyStop() {
        route.clearRoute();
        stop();
        route.setDirection(Direction.STABLE);
    }

    public void callEmergencyStop() {
        if (isMoving) {
            emergencyStop();
            isEmergencyStop = true;
        }
    }
}
package com.sytoss.edu2021.bom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sytoss.edu2021.common.Direction;
import com.sytoss.edu2021.common.EngineStatus;
import com.sytoss.edu2021.common.RouteBOM;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EngineBOM {
    private Integer id;

    private BuildingBOM building;
    @JsonIgnore
    private CabinBOM cabin;

    private EngineStatus status;
    @JsonIgnore
    private RouteBOM route;


    private Integer currentFloor = 1;

    public EngineBOM() {
        route = new RouteBOM();
    }

    public EngineBOM(int id) {
        this.id = id;
        route = new RouteBOM();
    }

    public EngineBOM(RouteBOM route, Integer currentFloor) {
        this.route = route;
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
        if(route.getDirection().equals(Direction.UP))
            status = EngineStatus.RUNNING_UP;
        if(route.getDirection().equals(Direction.DOWN))
            status = EngineStatus.RUNNING_DOWN;
        move();
    }

    public void stop() {
        status = EngineStatus.STOP;
    }

    private void emergencyStop() {
        route.clearRoute();
        stop();
        route.setDirection(Direction.STABLE);
    }

    public void callEmergencyStop() {
        if (status.equals(EngineStatus.RUNNING_UP) || status.equals(EngineStatus.RUNNING_DOWN)) {
            emergencyStop();
        }
    }
}
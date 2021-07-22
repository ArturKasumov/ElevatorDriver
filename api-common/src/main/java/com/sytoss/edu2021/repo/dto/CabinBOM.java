package com.sytoss.edu2021.repo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@ToString
public class CabinBOM {

    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private int number;
    //TODO: yevgenyv : remove it
    //@JsonIgnore
    @Getter
    @Setter
    private BuildingBOM building;


    @Setter
    private Integer[] floorButtons;

    @Setter
    private boolean isDoorOpened;

    @Setter
    private boolean isOverloaded;


    @Getter
    @Setter
    @JsonIgnore
    private EngineBOM engine = null;

    @JsonIgnore
    private Route route = null;

    public CabinBOM(int id) {
        this.id = id;
    }

    public CabinBOM() {
        engine = new EngineBOM(route, null, 1);
    }

    public CabinBOM(int number, BuildingBOM building) {
        this.number = number;
        this.building = building;
    }

    public CabinBOM(int startFloor, int endFloor) {
        route = new Route();
        engine = new EngineBOM(route, new ArrayList<Floor>(Math.abs(startFloor) + Math.abs(endFloor)), startFloor);
        setFloors(startFloor, endFloor);
    }

    public void startMovement() {
        engine.start();
    }

    private void setFloors(int startFloor, int endFloor) {

        int floorsNumber = endFloor - startFloor + 1;
        floorButtons = new Integer[floorsNumber];

        int curfloor = startFloor;
        for (int i = 0; i < floorsNumber; i++) {
            floorButtons[i] = curfloor;
            ++curfloor;
        }

        this.engine.setCurrentFloor(startFloor);
    }

    /*public String getBuildingInfo() {
        String result = "address: " + building.getAddress();
        return result;
    }*/

    /*public BuildingBOM getBuilding(){
        return building;
    }*/

    public void addFloorToStop(int floorNumber) {
        if (floorNumber < floorButtons[0] && floorNumber > floorButtons[floorButtons.length - 1]) {
            return;
        }
        route.addRoutFloor(engine.getCurrentFloor(), floorNumber);

    }

    public void openDoor() {
        if (!isDoorOpened)
            isDoorOpened = true;
    }

    public void closeDoor() {
        if (isDoorOpened) {
            isDoorOpened = false;
        }
    }

    public String displayCabinInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(route.getDirection());
        stringBuilder.append(" ");
        stringBuilder.append(engine.getCurrentFloor());
        if (isOverloaded) {
            stringBuilder.append(" Overloaded!!!");
        }

        return stringBuilder.toString();
    }

    public void callEmergencyStop() {
        engine.callEmergencyStop();
    }

    public boolean isValid() {
        return number > 0;
    }
}
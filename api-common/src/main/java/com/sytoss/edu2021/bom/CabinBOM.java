package com.sytoss.edu2021.bom;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class CabinBOM {

    private Integer id;

    private Integer number;

    private Boolean isDoorOpened;

    private Boolean isOverloaded;

    public CabinBOM() {
    }

    public CabinBOM(int number) {
        this.number = number;
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
        if (isOverloaded) {
            stringBuilder.append(" Overloaded!");
        }

        return stringBuilder.toString();
    }


    public boolean isValid() {
        return number > 0;
    }

}
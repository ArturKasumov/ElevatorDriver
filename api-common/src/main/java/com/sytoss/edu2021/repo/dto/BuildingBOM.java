package com.sytoss.edu2021.repo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class BuildingBOM {

    private Integer id;
    private String address;
    private int floorsAmount;
    @JsonIgnore
    private ArrayList<CabinBOM> cabins = new ArrayList<>();

    public BuildingBOM() {
    }
    public BuildingBOM(int id) {
        this.id = id;
    }

    public BuildingBOM(String address, int floorsAmount){
        this.address =address;
        this.floorsAmount = floorsAmount;
    }

    @JsonIgnore
    public List<CabinBOM> getCabins() {
        return Collections.unmodifiableList(cabins);
    }

    public List<String> getCabinsInfo() {
        List<String> cabinNumbers = new ArrayList<>();

        if(cabins!=null) {
            for (CabinBOM cabin : cabins) {
                String tmp = "number: " + cabin.getNumber() + " engine id: " + cabin.getEngine().getId();
                cabinNumbers.add(tmp);

            }
        }
        return Collections.unmodifiableList(cabinNumbers);
    }

    public void addCabin(CabinBOM cabin) {
        cabins.add(cabin);
    }

    public boolean isValid() {
        return !ObjectUtils.isEmpty(address) && floorsAmount > 1;
    }

    public CabinBOM findCabinByNumber(int cabinNumber) {
        for(CabinBOM cabin : cabins){
            if (cabin.getNumber() == cabinNumber)
                return cabin;
        }
        return null;
    }
    @JsonIgnore
    public Integer[] getCabinIdList(){
        Integer[] cabId = new Integer[cabins.size()];
        for (int i = 0; i < cabins.size(); i++) {
            cabId[i]=cabins.get(i).getId();
        }
        return cabId;
    }
}
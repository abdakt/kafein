package com.aaktas.service;

import com.aaktas.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ParkService {

    @Autowired
    private Park park;

    public String park(VehicleVO vehicleVO, Integer type){
        if(type == null || (type.intValue() < 1 && type.intValue() > 3)){
            return "Invalid type parameter";
        }

        Vehicle vehicle = null;
        boolean reserved = false;
        if(type.intValue() == 1){
            vehicle = new Car();
        } else if(type.intValue() == 2){
            vehicle = new Jeep();
        } else if(type.intValue() == 3){
            vehicle = new Truck();
        } else {
            throw new InvalidParameterException("Invalid vehicle type");
        }
        if(park.getSlotList().stream().filter(s -> s.getVehicle() != null && s.getVehicle().getPlate().equals(vehicleVO.getPlate())).findFirst().isPresent()){
            return "A vehicle with this plate already exists in the park";
        }
        if(vehicleVO.getPlate() == null || vehicleVO.getPlate().isBlank()){
            return "Vehicle plate can not be blank";
        }

        BeanUtils.copyProperties(vehicleVO, vehicle);
        List<Slot> lstSlot = park.getSlotList();
        int requiredSlot = vehicle.getWidth() + 1;
        int contiguousSlot = 0;
        for (int i = 0; i < lstSlot.size(); i++) {
            Slot slot = lstSlot.get(i);
            if(slot.getVehicle() == null){
                contiguousSlot++;
            } else {
                contiguousSlot = 0;
            }
            if(contiguousSlot == requiredSlot){
                for (int j = i - requiredSlot + 1; j < i + 1; j++) {
                    lstSlot.get(j).setVehicle(vehicle);
                    if(i == j){
                        lstSlot.get(j).setForBlank(true);
                    }
                }
                vehicle.setParkingDateTime(LocalDateTime.now());
                reserved = true;
                break;
            }
        }
        if(reserved){
            return "Allocated " + (requiredSlot - 1) + " slot.";
        } else {
            return "Garage is full";
        }
    }

    public String status(){
        StringBuilder stringBuilder = new StringBuilder();
        Vehicle vehicle = null;

        LinkedHashMap<String, List<Slot>> mapByVehicle = park.getSlotList().stream().filter(e -> e.getVehicle() != null)
                .collect(Collectors.groupingBy(g -> g.getVehicle().getPlate(), LinkedHashMap::new, Collectors.toList()));

        mapByVehicle.forEach((k,v) -> {
            stringBuilder.append(k + " " + v.get(0).getVehicle().getColor() + " [");
            String slotsOccupied = v.stream().filter(f -> !f.isForBlank()).map(i -> String.valueOf(i.getIndex() + 1)).collect(Collectors.joining(","));
            stringBuilder.append(slotsOccupied);
            stringBuilder.append("]");
            stringBuilder.append("\n");
        });

        return stringBuilder.toString();
    }

    public String leave(Integer parkIndex) {

        LinkedHashMap<String, List<Slot>> mapByVehicle = park.getSlotList().stream().filter(e -> e.getVehicle() != null)
            .collect(Collectors.groupingBy(g -> g.getVehicle().getPlate(), LinkedHashMap::new, Collectors.toList()));

        int index = 0;
        Vehicle vehicleToLeave = null;
        for (Map.Entry<String, List<Slot>> entry : mapByVehicle.entrySet()) {
            index++;
            if(index == parkIndex){
                vehicleToLeave = entry.getValue().get(0).getVehicle();
                break;
            }
        }
        if(vehicleToLeave == null){
            return "No vehicle found at given index:" + parkIndex;
        }

        mapByVehicle.get(vehicleToLeave.getPlate()).stream().forEach(s ->{ s.setVehicle(null); s.setForBlank(false);});
        return vehicleToLeave.getWidth() + " slots deallocated." + vehicleToLeave.getPlate() + " leaved from the park";
    }
}

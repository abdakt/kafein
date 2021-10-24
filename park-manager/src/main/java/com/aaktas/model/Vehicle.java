package com.aaktas.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class Vehicle {
    public String plate;
    public String color;
    public abstract int getWidth();
    public LocalDateTime parkingDateTime;
}

package com.aaktas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Truck extends Vehicle{

    @Override
    public int getWidth() {
        return 4;
    }
}

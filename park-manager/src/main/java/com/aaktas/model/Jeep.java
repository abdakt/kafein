package com.aaktas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Jeep extends Vehicle{

    @Override
    public int getWidth() {
        return 2;
    }

}

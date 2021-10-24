package com.aaktas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Slot {
    private int index;
    private Vehicle vehicle;
    private boolean forBlank;
}

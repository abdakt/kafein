package com.aaktas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Data
@Component
public class Park {

    private static int SLOT_NUM = 10;

    private String title;

    private List<Slot> slotList = new ArrayList<>();

    public Park(){
        for (int i = 0; i < SLOT_NUM; i++) {
            Slot slot = new Slot();
            slot.setIndex(i);
            slotList.add(slot);
        }
    }
}

package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.Item;

public class HealthPotion extends Item {
    public HealthPotion(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "healthPotion";
    }

}

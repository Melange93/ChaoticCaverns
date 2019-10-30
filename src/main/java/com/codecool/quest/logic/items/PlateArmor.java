package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.Item;

public class PlateArmor extends Item {
    public PlateArmor(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "plateArmor";
    }

}

package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.Item;

public class BreastPlate extends Item {
    public BreastPlate(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "breastPlate";
    }

}

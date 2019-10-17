package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.Item;

public class Crown extends Item {
    public Crown(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "crown";
    }

}

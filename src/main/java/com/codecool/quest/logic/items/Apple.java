package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.Item;

public class Apple extends Item {
    public Apple(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "apple";
    }

}
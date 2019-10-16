package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.Item;

public class Sword extends Item {
    public Sword(Cell cell) { super(cell);}

    public String getTileName() {
        return "sword";
    }
}

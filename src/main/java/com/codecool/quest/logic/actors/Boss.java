package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;

public class Boss extends Actor {
    public Boss(Cell cell) {
        super(cell, 7, 4, 0);
    }

    @Override
    public String getTileName() {
        return "boss";
    }
}
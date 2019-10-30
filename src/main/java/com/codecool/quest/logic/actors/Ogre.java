package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;

public class Ogre extends Actor {
    public Ogre(Cell cell) {
        super(cell, 20, 5, 0);
    }

    @Override
    public String getTileName() {
        return "ogre";
    }
}
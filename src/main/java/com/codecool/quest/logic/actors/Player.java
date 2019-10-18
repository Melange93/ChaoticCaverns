package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

import java.util.HashMap;

public class Player extends Actor {
    HashMap<String, Integer> inventory = new HashMap<String, Integer>();
    public Player(Cell cell) {
        super(cell);
    }
    public HashMap<String, Integer> Invetory() {
        return inventory;
    }
    public String getTileName() {
        return "player";
    }

    public void pickUp() {
        int count;
        if (this.getCell().getItem() != null) {
            String itemName = this.getCell().getItem().getTileName();
            if (inventory.containsKey(itemName)) {
                count = inventory.get(itemName);
                inventory.put(itemName, count + 1);
            } else {
                inventory.put(itemName, 1);
            }
            this.getCell().setItem(null);
        }
    }

}

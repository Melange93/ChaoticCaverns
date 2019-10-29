package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

import java.util.HashMap;
import java.util.Map;

public class Player extends Actor {
    private HashMap<String, Integer> inventory = new HashMap<String, Integer>();
    public Player(Cell cell) {
        super(cell, 10, 2, 0);
    }
    public HashMap<String, Integer> getInventory() {
        return inventory;
    }
    public String getTileName() {
        return "player";
    }

    public void itemChecker() {
        if (inventory.containsKey("sword")) {
            int currentDamage = this.getDamage();
            this.setDamage(currentDamage + 2);
        }
        if (inventory.containsKey("breastPlate")) {
            int currentArmor = this.getArmor();
            this.setArmor(currentArmor + 1);
        }
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
            itemChecker();
        }
    }

    public void attack(Cell cell,Actor monster) {
        int playerDamage = cell.getActor().getDamage();
        int monsterDamage = monster.getDamage();
        int playerHealth = cell.getActor().getHealth();
        int monsterHealth = monster.getHealth();
        int playerArmor = cell.getActor().getArmor();
        monster.setHealth(monsterHealth - playerDamage);
        if (monster.getHealth() <= 0) {
            monster.getCell().setActor(null);
        } else {
            cell.getActor().setHealth(playerHealth - (monsterDamage - playerArmor));
        }
    }

}

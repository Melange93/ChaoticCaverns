package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

import java.util.HashMap;
import java.util.Random;

public class Player extends Actor {
    private Random rand = new Random();
    private HashMap<String, Integer> inventory = new HashMap<String, Integer>();

    public Player(Cell cell) {
        super(cell, 10, 2, 0);
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public String getTileName() {
        if (inventory.containsKey("sword") && inventory.containsKey("plateArmor")) {
            return "playerArmorAndSword";
        }
        if (inventory.containsKey("plateArmor")) {
            return "playerArmor";
        }
        if (inventory.containsKey("sword")) {
            return "playerSword";
        }
        return "player";
    }

    public void itemChecker() {
        if (inventory.containsKey("sword")) {
            int minDamage = this.getMinDamage();
            this.setDamage(minDamage + 2);
        }
        if (inventory.containsKey("plateArmor")) {
            int currentArmor = this.getArmor();
            this.setArmor(currentArmor + 2);
        }
        if (inventory.containsKey("apple")) {
            int currentHealth = this.getHealth();
            this.getMoreHealth(2);
            inventory.remove("apple");
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

    private void getMoreHealth(int plusHealthAmount) {
        int currentHealth = this.getHealth();
        if (currentHealth + plusHealthAmount > getMaxHealth()) {
            this.setHealth(getMaxHealth());
        } else {
            this.setHealth(currentHealth + plusHealthAmount);
        }
    }

    public void usePotion() {
        if (inventory.containsKey("healthPotion")) {
            if (this.getHealth() < 10) {
                int numOfHealthPotions = inventory.get("healthPotion");
                if (numOfHealthPotions > 1) {
                    inventory.put("healthPotions", numOfHealthPotions - 1);
                    this.getMoreHealth(4);

                } else {
                    inventory.remove("healthPotion");
                    this.getMoreHealth(4);
                }

            }
        }
    }

    public void attack(Cell cell, Actor monster) {
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

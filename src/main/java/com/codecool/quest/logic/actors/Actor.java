package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.Drawable;

import java.util.Map;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health;
    private int damage;
    private int armor;

    public Actor(Cell cell, int health, int damage, int armor) {
        this.cell = cell;
        this.cell.setActor(this);
        this.health = health;
        this.damage = damage;
        this.armor = armor;
    }

    public void move(int dx, int dy) {
        if (this.getHealth() > 0) {
            Cell nextCell = cell.getNeighbor(dx, dy);
            if (nextCell.getTileName().equals("floor") && nextCell.getActor() == null) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }

    public int getHealth() { return health; }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() { return damage; }

    public void setDamage(int damage) { this.damage = damage; }

    public int getArmor() { return armor; }

    public void setArmor(int armor) { this.armor = armor; }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}

package com.codecool.quest.logic;

import com.codecool.quest.logic.EntranceType;

public class Door implements Drawable{
    private Cell cell;

    public Door(Cell cell) {
        this.cell = cell;
        this.cell.setDoor(this);
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    @Override
    public String getTileName() {
        return this.getCell().getDoorType().toString() + "door";
    }
}

package com.codecool.quest.logic.entrance;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;

public class Entrance implements Drawable {
    private EntranceType type;
    private Cell cell;

    public Entrance(Cell cell, EntranceType type) {
        this.cell = cell;
        this.type = type;
        this.cell.setEntrance(this);
    }

    public Cell getCell() {
        return cell;
    }

    public EntranceType getEntranceType() {
        return type;
    }

    public void setEntranceType(EntranceType type) {
        this.type = type;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }
}

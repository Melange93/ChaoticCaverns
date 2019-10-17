package com.codecool.quest.logic;

public enum EntranceType implements Drawable{
    CLOSED("closed"),
    OPEN("open");

    private final String tileName;

    EntranceType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }

}

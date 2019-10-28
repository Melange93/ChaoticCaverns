package com.codecool.quest.logic.entrance;

public enum EntranceType {
    CLOSED("closed"),
    OPEN("open"),
    PORTAL("portal");

    private final String tileName;

    EntranceType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}

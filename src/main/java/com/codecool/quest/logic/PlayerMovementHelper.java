package com.codecool.quest.logic;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.entrance.Entrance;
import com.codecool.quest.logic.entrance.EntranceType;

import java.util.HashMap;
import java.util.List;

public class PlayerMovementHelper {
    private Player player;
    private List<Entrance> entrances;

    public PlayerMovementHelper(Player player, List<Entrance> entrances) {
        this.player = player;
        this.entrances = entrances;
    }

    public boolean isAnEntrance(int dx, int dy) {
        Cell nextCell = player.getCell().getNeighbor(dx, dy);
        return nextCell.getEntrance() != null;
    }

    private boolean isAClosedDoor(int dx, int dy) {
        EntranceType nextCellEntranceType = player.getCell().getNeighbor(dx, dy).getEntrance().getEntranceType();
        return nextCellEntranceType == EntranceType.CLOSED;
    }

    private boolean haveKey() {
        return player.getInventory().containsKey("key");
    }

    private void openDoor(int dx, int dy) {
        Cell nextCell = player.getCell().getNeighbor(dx, dy);
        for (Entrance entrance : entrances) {
            if (entrance.getCell() == nextCell) {
                entrance.setEntranceType(EntranceType.OPEN);
            }
        }
    }

    private void decreaseKey() {
        HashMap<String, Integer> inventory = player.getInventory();
        if (inventory.get("key") > 1) {
            int keyAmount = inventory.get("key");
            inventory.put("key", keyAmount - 1);
        } else {
            inventory.remove("key");
        }
    }

    public boolean canMoveThroughTheDoor(int dx, int dy) {
        if (isAnEntrance(dx, dy)) {
            if (isAClosedDoor(dx, dy) && haveKey()) {
                openDoor(dx, dy);
                decreaseKey();
                return true;
            }
            return !isAClosedDoor(dx, dy);
        }
        return true;
    }

    public boolean isADownStair(int dx, int dy) {
        return player.getCell().getNeighbor(dx, dy).getEntrance().getEntranceType() == EntranceType.DOWN;
    }

    public boolean isAUpStair(int dx, int dy) {
        return player.getCell().getNeighbor(dx, dy).getEntrance().getEntranceType() == EntranceType.UP;
    }

}

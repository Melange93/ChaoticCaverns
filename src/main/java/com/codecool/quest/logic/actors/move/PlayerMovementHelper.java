package com.codecool.quest.logic.actors.move;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.entrance.Entrance;
import com.codecool.quest.logic.entrance.EntranceType;

import java.util.HashMap;
import java.util.List;

public class PlayerMovementHelper {
    private Player player;
    private int dx;
    private int dy;
    private List<Entrance> entrances;

    public PlayerMovementHelper(Player player, int dx, int dy, List<Entrance> entrances) {
        this.player = player;
        this.dx = dx;
        this.dy = dy;
        this.entrances = entrances;
    }

    public boolean isAnEntrance() {
        Cell nextCell = player.getCell().getNeighbor(dx, dy);
        return nextCell.getEntrance() != null;
    }

    public boolean isAClosedDoor() {
        EntranceType nextCellEntranceType = player.getCell().getNeighbor(dx, dy).getEntrance().getEntranceType();
        return nextCellEntranceType == EntranceType.CLOSED;
    }

    public boolean haveKey() {
        return player.getInventory().containsKey("key");
    }

    public void openDoor() {
        Cell nextCell = player.getCell().getNeighbor(dx, dy);
        for (Entrance entrance : entrances) {
            if (entrance.getCell() == nextCell) {
                entrance.setEntranceType(EntranceType.OPEN);
            }
        }
    }

    public void decreaseKey() {
        HashMap<String, Integer> inventory = player.getInventory();
        if (inventory.get("key") > 1) {
            int keyAmount = inventory.get("key");
            inventory.put("key", keyAmount - 1);
        } else {
            inventory.remove("key");
        }
    }

    public boolean canMoveThroughTheDoor() {
        if (isAnEntrance()) {
            if (isAClosedDoor() && haveKey()) {
                openDoor();
                decreaseKey();
                return true;
            }
            return !isAClosedDoor();
        }
        return true;
    }

    public String nextCellTileName() {
        return player.getCell().getNeighbor(dx, dy).getTileName();
    }

    public boolean nextCellIsNotAnActor() {
        return player.getCell().getNeighbor(dx, dy).getActor() == null;
    }

    public boolean canPlayerMove() {
        if (nextCellTileName().equals("floor") && nextCellIsNotAnActor() && canMoveThroughTheDoor()) {
            return true;
        }
        return false;
    }
}

package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;

import com.codecool.quest.logic.entrance.Entrance;

import com.codecool.quest.logic.items.Key;
import com.codecool.quest.logic.items.Sword;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private List<Skeleton> skeletons = new ArrayList<>();
    private Key key;
    private Sword sword ;
    private Entrance entrance;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setEntrance(Entrance entrance) {
        this.entrance = entrance;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public void addSkeleton(Skeleton skeleton) {
        skeletons.add(skeleton);
    }

    public List<Skeleton> getSkeleton() {
        return skeletons;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    public void setSword(Sword sword) { this.sword = sword; }

    public Sword getSword() { return sword; }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}

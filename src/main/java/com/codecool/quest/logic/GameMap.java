package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;

import com.codecool.quest.logic.entrance.Entrance;

import com.codecool.quest.logic.items.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    public Cell[][] cells;

    private Player player;
    private List<Actor> monsters = new ArrayList<>();
    private Key key;
    private Crown crown;
    private Sword sword ;
    private PlateArmor plateArmor;
    private HealthPotion healthPotion;
    private Apple apple;
    private List<Entrance> entrances = new ArrayList<>();

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

    public void deletePlayer() {
        this.player = null;
    }

    public Player getPlayer() {
        return player;
    }

    public void addEntrance(Entrance entrance) {
        entrances.add(entrance);
    }

    public List<Entrance> getEntrances() {
        return entrances;
    }

    public void addSkeleton(Skeleton skeleton) { monsters.add(skeleton); }

    public List<Actor> getMonsters() { return monsters; }

    public void setCrown(Crown key) {
        this.crown = crown;
    }

    public Crown getCrown() {
        return crown;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    public void setSword(Sword sword) { this.sword = sword; }

    public Sword getSword() { return sword; }

    public void setPlateArmor(PlateArmor plateArmor) { this.plateArmor = plateArmor; }

    public PlateArmor getPlateArmor() { return plateArmor; }

    public void setHealthPotion(HealthPotion healthPotion) { this.healthPotion = healthPotion; }

    public HealthPotion getHealthPotion() { return healthPotion; }

    public void setApple(Apple apple) { this.apple = apple; }

    public Apple getApple() { return apple; }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cells.length; i++) {
            sb.append(Arrays.toString(cells[i]));
            sb.append(" ");
        }
        return sb.toString();
    }

}

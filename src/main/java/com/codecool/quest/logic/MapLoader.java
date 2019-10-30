package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.entrance.Entrance;
import com.codecool.quest.logic.entrance.EntranceType;
import com.codecool.quest.logic.items.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class MapLoader {

    private static int gameLevel = 0;
    private static String loadMap;
    public static Player savePlayer;

    private static HashMap<Integer, GameMap> saveInvetory;
    private static int saveHealt;
    private static int saveArmor;
    private static int saveDamage;

    private static void getWhichMapLoad() {
        switch (gameLevel) {
            case 0:
                loadMap = "/map.txt";
                break;
            case 1:
                loadMap = "/map2.txt";
                break;
            default:
                loadMap = "/map.txt";
        }
    }

    public static int getGameLevel() {
        return gameLevel;
    }

    public static Player getSavePlayer() { return savePlayer; }

    public static void setSavePlayer(Player savePlayer) { MapLoader.savePlayer = savePlayer; }

    public static HashMap getSaveInvetory() {
        return saveInvetory;
    }

    public static void setSaveInvetory(HashMap invetory) {
        MapLoader.saveInvetory = invetory;
    }


    public static void downMapGameLevel() {
        gameLevel = gameLevel + 1;
    }

    public static void upMapGameLevel() {
        if (gameLevel > 0) {
            gameLevel = gameLevel - 1;
        }
    }

    public static GameMap loadMap() {
        getWhichMapLoad();
        InputStream is = MapLoader.class.getResourceAsStream(loadMap);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    EntranceType closed = EntranceType.CLOSED;
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.addSkeleton(new Skeleton(cell));
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            map.setKey(new Key(cell));
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            map.setSword(new Sword(cell));
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            map.setCrown(new Crown(cell));
                            break;
                        case 'd':
                            cell.setType(CellType.FLOOR);
                            map.addEntrance(new Entrance(cell, closed));
                            break;
                        case 't':
                            cell.setType(CellType.FLOOR);
                            map.addEntrance(new Entrance(cell, EntranceType.DOWN));
                            break;
                        case 'u':
                            cell.setType(CellType.FLOOR);
                            map.addEntrance(new Entrance(cell, EntranceType.UP));
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            if (savePlayer != null) {
                                getSavePlayer().setCell(cell);
                                map.setPlayer(savePlayer);
                            } else {
                                map.setPlayer(new Player(cell));
                            }
                            break;
                        case 'a':
                            cell.setType(CellType.FLOOR);
                            map.setPlateArmor(new PlateArmor(cell));
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            map.setHealthPotion(new HealthPotion(cell));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    public static int getSaveHealt() {
        return saveHealt;
    }

    public static void setSaveHealt(int saveHealt) {
        MapLoader.saveHealt = saveHealt;
    }

    public static int getSaveArmor() {
        return saveArmor;
    }

    public static void setSaveArmor(int saveArmor) {
        MapLoader.saveArmor = saveArmor;
    }

    public static int getSaveDamage() {
        return saveDamage;
    }

    public static void setSaveDamage(int saveDamage) {
        MapLoader.saveDamage = saveDamage;
    }
}

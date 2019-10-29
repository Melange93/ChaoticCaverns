package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.entrance.Entrance;
import com.codecool.quest.logic.entrance.EntranceType;
import com.codecool.quest.logic.items.Key;
import com.codecool.quest.logic.items.Sword;
import com.codecool.quest.logic.items.Crown;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {

    private static int gameLevel = 0;
    private static String loadMap;

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
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}

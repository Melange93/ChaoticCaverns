package com.codecool.quest;

import com.codecool.quest.logic.*;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.entrance.EntranceType;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.codecool.quest.logic.items.*;

import java.util.*;

public class Main extends Application {
    private Random rand = new Random();
    GameMap map = MapLoader.loadMap();
    PlayerMovementHelper movementHelper = new PlayerMovementHelper(map.getPlayer(), map.getEntrances());
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label armorLabel = new Label();
    Label damageLabel = new Label();
    Button pickUpButton = new Button();

    ListView<String> inventoryElementList = new ListView<>();
    ObservableList<String> items = FXCollections.observableArrayList();

    HashMap<Integer, GameMap> saveMaps = new HashMap<>();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Armor: "), 0, 1);
        ui.add(armorLabel, 1, 1);
        ui.add(new Label("Attack Damage: "), 0, 2);
        ui.add(damageLabel, 1, 2);

        ui.add(pickUpButton, 0, 3);
        pickUpButton.setFocusTraversable(false);
        pickUpButton.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    map.getPlayer().pickUp();
                    refreshInventory();
                });


        ui.add(new Label("Inventory: "), 0, 4);

        inventoryElementList.setItems(items);
        inventoryElementList.setFocusTraversable(false);
        inventoryElementList.setPrefSize(120, 25);

        ui.add(inventoryElementList, 0, 6);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();
    }


    private boolean nextIsAMonster(int dx, int dy) {
        Cell playerNextCell = map.getPlayer().getCell().getNeighbor(dx, dy);
        return playerNextCell.getActor() != null;
    }

    private void refreshInventory() {
        items.clear();
        for (Map.Entry<String, Integer> stringIntegerEntry : map.getPlayer().getInventory().entrySet()) {
            String kayValueStringPair = ((Map.Entry) stringIntegerEntry).getKey().toString() + ": " + ((Map.Entry) stringIntegerEntry).getValue().toString();
            items.add(kayValueStringPair);
        }
        inventoryElementList.setPrefSize(120, 25 * (map.getPlayer().getInventory().size() + 1));
    }

    private boolean gameOver() {
        if (map.getPlayer().getHealth() <= 0) {
            return true;
        } else {
            return false;
        }

    }

    private void dropLoot(Cell cell) {
        if (cell.getActor() == null) {
            int dropChance = rand.nextInt(100);
            if (dropChance < 99) {
                // here the game can choose from different items with rand maybe
                int randItem = rand.nextInt(2);
                switch (randItem) {
                    case 0:
                        map.setApple(new Apple(cell));
                        break;
                    case 1:
                        map.setCrown(new Crown(cell));
                        break;
                }
            }
        }

    }


    public void saveMap() {
        //MapLoader.savePlayer = map.getPlayer();
        MapLoader.setSaveInvetory(map.getPlayer().getInventory());
        MapLoader.setSaveHealt(map.getPlayer().getHealth());
        MapLoader.setSaveDamage(map.getPlayer().getDamage());
        MapLoader.setSaveArmor(map.getPlayer().getArmor());
        if (saveMaps.containsKey(MapLoader.getGameLevel())) {
            saveMaps.put(MapLoader.getGameLevel(), map);
        } else {
            int currentLevel = MapLoader.getGameLevel();
            saveMaps.put(currentLevel, map);
        }
    }

    public void reloadMap() {
        int gameLevel = MapLoader.getGameLevel();
        if (saveMaps.containsKey(gameLevel)) {
            map = saveMaps.get(gameLevel);
            map.getPlayer().setInventory(MapLoader.getSaveInvetory());
            map.getPlayer().setHealth(MapLoader.getSaveHealt());
            map.getPlayer().setDamage(MapLoader.getSaveDamage());
            map.getPlayer().setArmor(MapLoader.getSaveArmor());
        } else {
            map = MapLoader.loadMap();
            map.getPlayer().setInventory(MapLoader.getSaveInvetory());
            map.getPlayer().setHealth(MapLoader.getSaveHealt());
            map.getPlayer().setDamage(MapLoader.getSaveDamage());
            map.getPlayer().setArmor(MapLoader.getSaveArmor());
        }
        refresh();
    }


    public void movementBetweenLevels() {
        Cell cell = map.getPlayer().getCell();
        if (cell.getEntrance() != null) {
            if (cell.getEntrance().getEntranceType() == EntranceType.DOWN){
                saveMap();
                MapLoader.downMapGameLevel();
                reloadMap();
            } else if (cell.getEntrance().getEntranceType() == EntranceType.UP) {
                saveMap();
                MapLoader.upMapGameLevel();
                reloadMap();
            }

        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case Q:
                map.getPlayer().usePotion();
                refreshInventory();
                refresh();
                break;
            case E:
                map.getPlayer().pickUp();
                refreshInventory();
                refresh();
                break;
            case W:
                if (movementHelper.canMoveThroughTheDoor(0, -1)) {
                    refreshInventory();
                    map.getPlayer().move(0, -1);
                    movementBetweenLevels();
                }
                refresh();
                break;
            case S:
                if (movementHelper.canMoveThroughTheDoor(0, 1)) {
                    refreshInventory();
                    map.getPlayer().move(0, 1);
                    movementBetweenLevels();
                }
                refresh();
                break;
            case A:
                if (movementHelper.canMoveThroughTheDoor(-1, 0)) {
                    refreshInventory();
                    map.getPlayer().move(-1, 0);
                    movementBetweenLevels();
                }
                refresh();
                break;
            case D:
                if (movementHelper.canMoveThroughTheDoor(1, 0)) {
                    refreshInventory();
                    map.getPlayer().move(1, 0);
                    movementBetweenLevels();
                }
                refresh();
                break;
            case UP:
                if (nextIsAMonster(0, -1)) {
                    Cell playerCell = map.getPlayer().getCell();
                    for (Actor monster : map.getMonsters()) {
                        if (monster.getCell().equals(playerCell.getNeighbor(0, -1))) {
                            map.getPlayer().attack(playerCell, monster);
                            if (gameOver()) {
                                playerCell.setActor(null);
                            }
                            dropLoot(monster.getCell());
                        }
                    }
                }
                refresh();
                break;
            case DOWN:
                if (nextIsAMonster(0, 1)) {
                    Cell playerCell = map.getPlayer().getCell();
                    for (Actor monster : map.getMonsters()) {
                        if (monster.getCell().equals(playerCell.getNeighbor(0, 1))) {
                            map.getPlayer().attack(playerCell, monster);
                            if (gameOver()) {
                                playerCell.setActor(null);
                            }
                            dropLoot(monster.getCell());
                        }
                    }


                }
                refresh();
                break;
            case RIGHT:
                if (nextIsAMonster(1,0)) {
                    Cell playerCell = map.getPlayer().getCell();
                    for (Actor monster: map.getMonsters()) {
                        if (monster.getCell().equals(playerCell.getNeighbor(1, 0))) {
                            map.getPlayer().attack(playerCell, monster);
                            if (gameOver()) {
                                playerCell.setActor(null);
                            }
                            dropLoot(monster.getCell());
                        }
                    }


                }
                refresh();
                break;
            case LEFT:
                if (nextIsAMonster(-1,0)) {
                    Cell playerCell = map.getPlayer().getCell();
                    for (Actor monster: map.getMonsters()) {
                        if (monster.getCell().equals(playerCell.getNeighbor(-1, 0))) {
                            map.getPlayer().attack(playerCell, monster);
                            if (gameOver()) {
                                playerCell.setActor(null);
                            }
                            dropLoot(monster.getCell());
                        }
                    }


                }
                refresh();
                break;
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else if (cell.getEntrance() != null) {
                    Tiles.drawTile(context, cell.getEntrance(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        armorLabel.setText("" + map.getPlayer().getArmor());
        damageLabel.setText("" + map.getPlayer().getDamage());
        pickUpButton.setText("Pick up");
    }
}

package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.entrance.Entrance;
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

import java.util.Map;

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Button pickUpButton = new Button();

    ListView<String> inventoryElementList = new ListView<>();
    ObservableList<String> items = FXCollections.observableArrayList ();


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

        ui.add(pickUpButton, 0, 1);
        pickUpButton.setFocusTraversable(false);
        pickUpButton.addEventHandler(MouseEvent.MOUSE_PRESSED,
            e -> {
                map.getPlayer().pickUp();
                refreshInventory();
            });


        ui.add(new Label("Inventory: "), 0, 2);

        inventoryElementList.setItems(items);
        inventoryElementList.setFocusTraversable(false);
        inventoryElementList.setPrefSize(120, 25);

        ui.add(inventoryElementList, 0, 4);

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
    /*
    private boolean nextIsAWall(int dx, int dy) {
        String nextCellType = map.getPlayer().getCell().getNeighbor(dx, dy).getTileName();
        return !nextCellType.equals("floor");
    }

    private boolean nextIsASkeleton(int dx, int dy) {
        Cell playerNextCell = map.getPlayer().getCell().getNeighbor(dx, dy);
        return playerNextCell.getActor() != null;
    }

     */

    private void refreshInventory() {
        items.clear();
        for (Map.Entry<String, Integer> stringIntegerEntry : map.getPlayer().Invetory().entrySet()) {
            String kayValueStringPair = ((Map.Entry) stringIntegerEntry).getKey().toString() + ": " + ((Map.Entry) stringIntegerEntry).getValue().toString();
            items.add(kayValueStringPair);
        }
        inventoryElementList.setPrefSize(120, 25 *  (map.getPlayer().Invetory().size() + 1));
    }

    private boolean nextIsClosedDoor(int dx, int dy) {
        Cell nextCellCoord = map.getPlayer().getCell().getNeighbor(dx, dy);
        if (nextCellCoord.getEntrance() != null) {
            String nextCellIsDoorType = nextCellCoord.getEntrance().getEntranceType().getTileName();
            if (nextCellIsDoorType.equals("closed") && map.getPlayer().Invetory().containsKey("key")) {
                for (Entrance entrance : map.getEntrances()) {
                    if (entrance.getCell() == nextCellCoord) {
                       entrance.setEntranceType(EntranceType.OPEN);
                       if (map.getPlayer().Invetory().get("key") > 1) {
                           int keyAmount = map.getPlayer().Invetory().get("key");
                           map.getPlayer().Invetory().put("key", keyAmount - 1);
                       } else {
                           map.getPlayer().Invetory().remove("key");
                       }
                        refreshInventory();
                        return true;
                    }
                }
            }
            return nextCellIsDoorType.equals("open");
        }

        return true;
    }



    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case E:
                map.getPlayer().pickUp();
                refreshInventory();
                refresh();
                break;
            case UP:
                if (nextIsClosedDoor(0, -1)) {
                    map.getPlayer().move(0, -1);
                }
                refresh();
                break;
            case DOWN:
                if (nextIsClosedDoor(0, 1)) {
                    map.getPlayer().move(0, 1);
                }
                refresh();
                break;
            case LEFT:
                if (nextIsClosedDoor(-1, 0)) {
                    map.getPlayer().move(-1, 0);
                }
                refresh();
                break;
            case RIGHT:
                if (nextIsClosedDoor(1, 0)) {
                    map.getPlayer().move(1,0);
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
                }
                else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        pickUpButton.setText("Pick up");
    }
}

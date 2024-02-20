package Map;

import Game.GameController;
import Unit.Hero.Padoru;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameGui extends StackPane {
    private final GameMap gameMap = new GameMap();

    public GameGui() {
        setPrefHeight(720);
        setPrefWidth(1280);

        Button spawnHero = new Button("Spawn Hero");
        spawnHero.setOnAction(e -> {
            GameController.getInstance().spawn(new Padoru());
        });

        ScrollPane scrollPane = new ScrollPane(gameMap);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.setVmax(0);
        scrollPane.setPannable(true);
        scrollPane.setCursor(Cursor.DEFAULT);
        setAlignment(spawnHero, Pos.TOP_LEFT);
        setMargin(spawnHero, new Insets(10));
        getChildren().addAll(scrollPane, spawnHero);
    }

    public GameMap getGameMap() {
        return gameMap;
    }
}

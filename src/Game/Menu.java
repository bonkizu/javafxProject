package Game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menu extends VBox {

    private static Stage primaryStage;
    private static Menu instance;
    public Menu(Stage primaryStage) {
        Menu.primaryStage = primaryStage;
        setPrefWidth(1280);
        setPrefHeight(720);
        setSpacing(20);
        setAlignment(Pos.CENTER);
        setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        Button start = new Button("Start");
        String style = "-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: #233142; -fx-pref-width: 200px; -fx-font-weight:bold;";
        start.setStyle(style);
        Button stop = new Button("Exit");
        stop.setStyle(style);

        start.setOnAction(e -> startNewGame());
        start.setOnMouseEntered(e -> start.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: #3E5C76; -fx-pref-width: 200px; -fx-font-weight:bold;"));
        start.setOnMouseExited(e -> start.setStyle(style));
        stop.setOnAction(e -> System.exit(0));
        stop.setOnMouseEntered(e -> stop.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: #3E5C76; -fx-pref-width: 200px; -fx-font-weight:bold;"));
        stop.setOnMouseExited(e -> stop.setStyle(style));
        getChildren().addAll(start, stop);
        instance = this;
    }
    public void startNewGame(){
        GameController.getInstance().reset();
        GameController gameInstance = GameController.getInstance();
        primaryStage.setScene(new Scene(gameInstance.getGameGui(), 1280, 720));
    }
    public static Menu getInstance() {
        if(instance==null) return new Menu(primaryStage);
        return instance;
    }
}

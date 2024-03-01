package Map;

import Game.GameController;
import Unit.Hero.Padoru;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.animation.AnimationTimer;
import javafx.scene.text.Text;

public class GameGui extends StackPane {
    private final GameMap gameMap = new GameMap();
    private Text playerMoney = new Text("Player's money: ");

    public GameGui() {
        setPrefHeight(720);
        setPrefWidth(1280);

        Button spawnHero = new Button("Spawn Hero");
        spawnHero.setOnAction(e -> {
            GameController.getInstance().spawn(new Padoru());
        });

        Button Cooldown = new Button("Cooldown");
        Cooldown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!Cooldown.isDisabled() && GameController.getInstance().getMoney() >= 200){
                    GameController.getInstance().decreaseMoney(200);
                    GameController.getInstance().spawn(new Padoru());
                    startCooldownTimer(1000, Cooldown);
                }
            }
        });


        ScrollPane scrollPane = new ScrollPane(gameMap);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.setVmax(0);
        scrollPane.setPannable(true);
        scrollPane.setCursor(Cursor.DEFAULT);
        setAlignment(spawnHero, Pos.TOP_LEFT);
        setAlignment(Cooldown, Pos.TOP_RIGHT);
        setMargin(playerMoney, new Insets(10));
        setMargin(spawnHero, new Insets(10));
        setMargin(Cooldown, new Insets(10));
        setAlignment(playerMoney, Pos.TOP_CENTER);
        getChildren().addAll(scrollPane, spawnHero, Cooldown, playerMoney);
    }

    private void startCooldownTimer(int CooldownTime, Button button ) {
        button.setDisable(true); // Disable button immediately

        Thread timer = new Thread(()->{
            try{
                Thread.sleep(2000);
            } catch (Exception ex){
                ex.printStackTrace();
            }
            Platform.runLater(()->{
                button.setDisable(false);
            });
        });
        timer.start();
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setPlayerMoney(int money){
        playerMoney.setText("Player's money: " + money);
    }
}

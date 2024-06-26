package Map;

import Game.GameController;
import Unit.Hero.*;
import Utils.GameUtils;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameGui extends StackPane {
    private final GameMap gameMap = new GameMap();
    private Text playerMoney = new Text("0");
    private boolean isVolumeOn = true;
    private ImageView volumeImg;

    public GameGui() {
        setPrefHeight(720);
        setPrefWidth(1280);

        ImageView coin = new ImageView(GameUtils.setImageByPath("coin.png"));
        coin.setFitHeight(30);
        coin.setPreserveRatio(true);
        HBox moneyBox = new HBox(10);
        moneyBox.getChildren().addAll(coin, playerMoney);
        moneyBox.setMaxHeight(30);
        moneyBox.setMaxWidth(200);
        moneyBox.setAlignment(Pos.CENTER);
        setMargin(moneyBox, new Insets(10));
        setAlignment(moneyBox, Pos.TOP_RIGHT);
        moneyBox.setStyle("-fx-background-color: rgba(255, 204, 102, 0.5);" +
                "-fx-border-color: black;" +                       // Black border color
                "-fx-border-width: 2px;" +                         // Border width
                "-fx-border-radius: 10px;");


        FlowPane heroesPanel = new FlowPane();
        heroesPanel.setHgap(10);
        heroesPanel.setPrefWrapLength(500);

        heroesPanel.setMaxWidth(900);
        heroesPanel.setMaxHeight(150);
        heroesPanel.getChildren().add(createHeroSpawner(new Golem()));
        heroesPanel.getChildren().add(createHeroSpawner(new Spider()));
        heroesPanel.getChildren().add(createHeroSpawner(new Robot()));
        heroesPanel.getChildren().add(createHeroSpawner(new Drone()));
        heroesPanel.getChildren().add(createHeroSpawner(new Dog()));


        ScrollPane scrollPane = new ScrollPane(gameMap);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.setVmax(0);
        scrollPane.setPannable(true);
        scrollPane.setCursor(Cursor.DEFAULT);

        setAlignment(heroesPanel, Pos.BOTTOM_CENTER);
        setMargin(heroesPanel, new Insets(10, 10, 50, 10));

        volumeImg = new ImageView(GameUtils.setImageByPath("volume-up.png"));
        volumeImg.setOnMouseClicked(event -> {
            if (!GameController.getInstance().isGameOver()) {
                if (isVolumeOn) {
                    GameController.getInstance().stopBgm();
                } else {
                    GameController.getInstance().startBgm();
                }
            }
            isVolumeOn = !isVolumeOn;
            volumeImg.setImage(GameUtils.setImageByPath(isVolumeOn ? "volume-up.png" : "volume-down.png"));
        });
        volumeImg.setFitWidth(50);
        volumeImg.setFitHeight(50);
        volumeImg.setTranslateX(570);
        volumeImg.setTranslateY(280);
        volumeImg.setEffect(new javafx.scene.effect.DropShadow(2, Color.DARKBLUE));
        getChildren().addAll(scrollPane, heroesPanel, moneyBox, volumeImg);
    }

    private void startCooldownTimer(int CooldownTime, Button button) {
        button.setDisable(true); // Disable button immediately

        Thread timer = new Thread(() -> {
            try {
                Thread.sleep(CooldownTime);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Platform.runLater(() -> {
                button.setDisable(false);
            });
        });
        timer.start();
    }

    private StackPane createHeroSpawner(BaseHero hero) {
        StackPane stackPane = new StackPane();
        stackPane.setBackground(Background.fill(Color.ORANGE));
        stackPane.setStyle("-fx-border-color: black; -fx-border-width: 5px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-background-color: rgba(255, 153, 102,  0.5);");
        Button button = new Button();
        button.setPrefWidth(150);
        button.setPrefHeight(150);
        button.setBackground(Background.fill(Color.TRANSPARENT));
        button.setText("Cost : " + hero.getCost());
        button.setAlignment(Pos.TOP_CENTER);
        button.setStyle(" -fx-font-weight: bold; -fx-font-size: 20;");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!button.isDisabled() && GameController.getInstance().getMoney() >= hero.getCost() && !GameController.getInstance().isGameOver()) {
                    GameController.getInstance().decreaseMoney(hero.getCost());
                    GameController.getInstance().spawn(hero.clone());
                    startCooldownTimer(hero.getCooldown(), button);
                }
            }
        });

        stackPane.getChildren().addAll(hero.getImageView(), button);
        return stackPane;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setPlayerMoney(int money) {
        playerMoney.setText("" + money);
        playerMoney.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        playerMoney.setFill(Color.WHITE);
    }
}

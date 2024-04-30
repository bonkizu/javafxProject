package Map;

import Unit.Enemy.EnemyTower;
import Unit.Hero.HeroTower;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.awt.*;

public class GameMap extends GridPane {
    public GameMap() {
        setPrefHeight(720);
        setPrefWidth(2000);
        setBackground(Background.fill(Color.DIMGRAY));
//        setStyle("-fx-background-image: url('BG.png');" +
//                "-fx-background-size: cover;");
    }

    public HeroTower createHeroTower() {
        HeroTower tower = new HeroTower();
        tower.getImageView().setTranslateY(40);
        getChildren().add(tower.getImageView());
        return tower;
    }

    public EnemyTower createEnemyTower() {
        EnemyTower tower = new EnemyTower();
        tower.getImageView().setTranslateY(40);
        tower.getImageView().setTranslateX(1700);
        getChildren().add(tower.getImageView());
        return tower;
    }
}

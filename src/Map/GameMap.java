package Map;

import Unit.Enemy.EnemyTower;
import Unit.Hero.HeroTower;
import Utils.GameUtils;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.awt.*;

public class GameMap extends GridPane {
    public GameMap() {
        setPrefHeight(720);
        setPrefWidth(2000);
        setBackground(Background.fill(Color.DIMGRAY));

        ImageView bg = new ImageView(GameUtils.setImageByPath("BG.png"));
        setAlignment(Pos.TOP_CENTER);
        bg.setFitHeight(470);
        bg.setFitWidth(2000);
        getChildren().add(bg);
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

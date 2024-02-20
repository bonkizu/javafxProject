package Map;

import Unit.Enemy.BaseEnemy;
import Unit.Enemy.Wg;
import Unit.Hero.BaseHero;
import Unit.Hero.Padoru;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GameMap extends GridPane {
    public GameMap() {
        setPrefHeight(720);
        setPrefWidth(2000);
        createHeroBase();
        createEnemyBase();
    }

    private void createHeroBase() {
        ImageView base = new ImageView(new Image("base.png"));
        base.setFitWidth(300);
        base.setPreserveRatio(true);
        base.setTranslateY(400);
        getChildren().add(base);
    }

    private void createEnemyBase() {
        ImageView base = new ImageView(new Image("base.png"));
        base.setFitWidth(300);
        base.setPreserveRatio(true);
        base.setTranslateY(400);
        base.setTranslateX(1700);
        getChildren().add(base);
    }
}

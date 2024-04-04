package Map;

import Unit.Enemy.BaseEnemy;
import Unit.Enemy.EnemyTower;
import Unit.Enemy.Wg;
import Unit.Hero.BaseHero;
import Unit.Hero.HeroTower;
import Unit.Hero.Padoru;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GameMap extends GridPane {
    public GameMap() {
        setPrefHeight(720);
        setPrefWidth(2000);
    }

    public HeroTower createHeroTower() {
        HeroTower tower = new HeroTower();
        tower.getImageView().setTranslateY(200);
        getChildren().add(tower.getImageView());
        return tower;
    }

    public EnemyTower createEnemyTower() {
        EnemyTower tower = new EnemyTower();
        tower.getImageView().setTranslateY(200);
        tower.getImageView().setTranslateX(1700);
        getChildren().add(tower.getImageView());
        return tower;
    }
}

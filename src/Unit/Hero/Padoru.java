package Unit.Hero;

import Game.GameController;
import Unit.Enemy.BaseEnemy;
import Unit.Type.Attackable;
import Utils.GameUtils;
import Utils.UnitState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Padoru extends BaseHero implements Attackable {
    public Padoru() {
        super(10, 10, 100, 10, 10, "Padoru", 1.5, "padoru.gif");
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            checkEnemy();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void checkEnemy() {
        BaseEnemy enemy = GameController.getInstance().getEnemies().get(0);
        if(GameUtils.inRange(this, enemy)) {
            System.out.println(enemy.getImageView().getTranslateX());
            System.out.println(getImageView().getTranslateX());
            stopMoving();
            Rectangle sword = new Rectangle(20, 100);
            sword.setTranslateX(getImageView().getTranslateX()+50);
            sword.setTranslateY(getImageView().getTranslateY()+20);
            GameController.getInstance().getGameMap().getChildren().add(sword);
        }
    }

    @Override
    public void attack() {

    }
}

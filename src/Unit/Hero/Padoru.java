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
    private Timeline checking;
    public Padoru() {
        super(10, 10, 100, 40, 10, "Padoru", 1.5, "padoru.gif");
        checking = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            check();
        }));
        checking.setCycleCount(Timeline.INDEFINITE);
        checking.play();
    }

    @Override
    public void check() {
        if(GameUtils.inRange(this, GameController.selectedEnemy)) {
            System.out.println(GameController.selectedEnemy.getImageView().getTranslateX());
            System.out.println(getImageView().getTranslateX());
            stopMoving();
            attack();
        }
    }

    @Override
    public void attack() {
        
    }
}

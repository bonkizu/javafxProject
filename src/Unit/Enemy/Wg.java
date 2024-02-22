package Unit.Enemy;

import Game.GameController;
import Unit.Type.Attackable;
import Utils.GameUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Wg extends BaseEnemy implements Attackable {

    private Timeline checking;
    public Wg() {
        super(10, 10, 100, 50, 10, "Ui", 1.5, "wg.gif");

        checking = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            check();
        }));
        checking.setCycleCount(Timeline.INDEFINITE);
        checking.play();
    }
    @Override
    public void check() {
        if(GameUtils.inRange(this, GameController.selectedHero)) {
            System.out.println(GameController.selectedEnemy.getImageView().getTranslateX());
            System.out.println(getImageView().getTranslateX());
            stopMoving();
            attack();
            checking.stop();
        }
    }

    @Override
    public void attack() {

    }
}

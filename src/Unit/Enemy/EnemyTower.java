package Unit.Enemy;

import Unit.BaseUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class EnemyTower extends BaseEnemy {
    public EnemyTower() {
        super(0, 100, 10000, 0, "EnemyTower", 1, "EnemyTower/idle.gif", 1, 1, 1);
        getImageView().setFitWidth(300);
        getImageView().setPreserveRatio(true);
    }


}

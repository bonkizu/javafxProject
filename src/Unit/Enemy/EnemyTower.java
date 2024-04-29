package Unit.Enemy;

import Game.GameController;
import Unit.BaseUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class EnemyTower extends BaseEnemy {
    public EnemyTower() {
        super(100, 100, 10000, 0, 1, 0, "EnemyTower", 1, "EnemyTower/idle.gif", 0, 0);
        getImageView().setFitWidth(300);
        getImageView().setPreserveRatio(true);
    }

    //not working the timeline was stopped before running this
    @Override
    public void destroyed() {
        super.destroyed();
        getImageView().setImage(new Image("EnemyTower/dead.gif"));
        Timeline delete = new Timeline(new KeyFrame(Duration.millis(1800), e -> {
            GameController.getInstance().getGameMap().getChildren().remove(getImageView());
        }));
        delete.play();
    }
}

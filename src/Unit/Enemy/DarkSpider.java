package Unit.Enemy;

import Game.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class DarkSpider extends BaseEnemy {

    public DarkSpider() {
        super(1, 1, 100, 20, 1, 0, "Golem", 10, "Spider/idle.gif", 1000, 1600);
        getImageView().setFitWidth(200);
        getImageView().setPreserveRatio(true);
    }

    //Change this later
    @Override
    public void destroyed() {
        super.destroyed();
        getImageView().setImage(new Image("Spider/dead.gif"));
        Timeline delete = new Timeline(new KeyFrame(Duration.millis(1800), e -> {
            GameController.getInstance().getGameMap().getChildren().remove(getImageView());
        }));
        delete.play();
    }
}

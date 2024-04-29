package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class HeroTower extends BaseHero {
    public HeroTower() {
        super(1000, 1000, 10000, 0, 1, 0, 0, "HeroTower", 1, "HeroTower/idle.gif", 1000, 1600);
        getImageView().setFitWidth(300);
        getImageView().setPreserveRatio(true);
    }

    @Override
    public BaseHero clone() {
        return null;
    }

    @Override
    public void destroyed() {
        super.destroyed();
        getImageView().setImage(new Image("HeroTower/dead.gif"));
        Timeline delete = new Timeline(new KeyFrame(Duration.millis(1800), e -> {
            GameController.getInstance().getGameMap().getChildren().remove(getImageView());
        }));
        delete.play();
    }
}

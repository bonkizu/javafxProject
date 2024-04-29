package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Type.SpecialEffect;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Spider extends BaseHero {

    public Spider() {
        super(1000, 40, 100, 60, 10, 0, 500, "Spider", 0.8, "Spider/idle.gif", 1000, 1600);
        getImageView().setFitWidth(150);
        getImageView().setPreserveRatio(true);
    }

    @Override
    public BaseHero clone() {
        return new Spider();
    }

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

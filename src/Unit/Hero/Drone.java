package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Type.SpecialEffect;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Drone extends BaseHero implements SpecialEffect {

    public Drone() {
        super(1000, 40, 100, 60, 10, 0, 500, "Drone", 1.5, "Drone/idle.gif", 1000, 1600);
        getImageView().setFitWidth(150);
        getImageView().setPreserveRatio(true);
    }

    @Override
    public BaseHero clone() {
        return new Drone();
    }

    @Override
    public void showEffect(BaseUnit target) {
        ImageView effect = new ImageView();
        effect.setFitWidth(150);
        effect.setPreserveRatio(true);

        Timeline deleteEffect = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            GameController.getInstance().getGameMap().getChildren().remove(effect);
        }));
        Timeline addEffect = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            effect.setImage(new Image("Drone/effect.gif"));
            effect.setTranslateX(target.getImageView().getTranslateX() + 150);
            effect.setTranslateY(target.getImageView().getTranslateY() + 2);
            GameController.getInstance().getGameMap().getChildren().add(effect);
            deleteEffect.play();
        }));
        addEffect.play();
    }
}

package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Type.SpecialEffect;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Robot extends BaseHero implements SpecialEffect {
    public Robot() {
        super(1000, 40, 100, 60, 10, 150, 500, "Robot", 1.4, "Robot/idle.gif", 1000, 1600);
    }
    @Override
    public BaseHero clone() {
        return new Robot();
    }

    @Override
    public void showEffect(BaseUnit target) {
        ImageView effect = new ImageView(new Image("Robot/effect.gif"));
        effect.setFitWidth(150);
        effect.setPreserveRatio(true);

        Timeline deleteEffect = new Timeline(new KeyFrame(Duration.millis(600), e -> {
            GameController.getInstance().getGameMap().getChildren().remove(effect);
        }));
        Timeline addEffect = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            effect.setTranslateX(target.getImageView().getTranslateX() + 80);
            effect.setTranslateY(target.getImageView().getTranslateY() + 10);
            GameController.getInstance().getGameMap().getChildren().add(effect);
            deleteEffect.play();
        }));
        addEffect.play();
    }
}

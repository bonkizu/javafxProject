package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Enemy.BaseEnemy;
import Unit.Type.SpecialEffect;
import Utils.UnitState;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

public class Golem extends BaseHero implements SpecialEffect {
    public Golem() {
        super(2000, 40, 100, 60, 10, 0, 500, "Golem", 1, "Golem/idle.gif", 1000, 1600);
        getImageView().setFitWidth(200);
        getImageView().setPreserveRatio(true);
    }

    @Override
    public BaseHero clone() {
        return new Golem();
    }

    @Override
    public void showEffect(BaseUnit target) {
        ImageView effect = new ImageView(new Image("Golem/effect.gif"));
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
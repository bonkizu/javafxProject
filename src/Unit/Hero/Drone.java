package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Type.SpecialEffect;
import Utils.GameUtils;
import Utils.UnitState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Drone extends BaseHero implements SpecialEffect {
    public Drone() {
        super(100, 20, 200, 50, 100, 500, "Drone", 2.4, "Drone/idle.gif", 200, 1600, 2400);
        getImageView().setFitWidth(100);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(15);
    }

    @Override
    public BaseHero clone() {
        Drone drone = new Drone();
        drone.getImageView().setFitWidth(150);
        drone.getImageView().setPreserveRatio(true);
        drone.getImageView().setTranslateY(150);
        return drone;
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
            if (getState() != UnitState.DEAD) {
                effect.setImage(GameUtils.setImageByPath("Drone/effect.gif"));
                effect.setTranslateX(target.getImageView().getTranslateX() + 150);
                effect.setTranslateY(target.getImageView().getTranslateY() + 2);
                GameController.getInstance().getGameMap().getChildren().add(effect);
                deleteEffect.play();
            }
        }));
        addEffect.play();
    }
}

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

public class Robot extends BaseHero implements SpecialEffect {
    public Robot() {
        super(600, 100, 2000, 20, 500, 500, "Robot", 2.8, "Robot/idle.gif", 2000, 1600, 2000);
        getImageView().setFitWidth(150);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(0);
    }

    @Override
    public BaseHero clone() {
        Robot robot = new Robot();
        robot.getImageView().setFitWidth(300);
        robot.getImageView().setPreserveRatio(true);
        robot.getImageView().setTranslateY(120);
        return robot;
    }

    @Override
    public void showEffect(BaseUnit target) {
        ImageView effect = new ImageView(GameUtils.setImageByPath("Robot/effect.gif"));
        effect.setFitWidth(300);
        effect.setFitHeight(120);

        Timeline deleteEffect = new Timeline(new KeyFrame(Duration.millis(600), e -> {
            GameController.getInstance().getGameMap().getChildren().remove(effect);
        }));
        Timeline addEffect = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            if (getState() != UnitState.DEAD) {
                effect.setTranslateX(target.getImageView().getTranslateX() + 100);
                effect.setTranslateY(target.getImageView().getTranslateY() + 10);
                GameController.getInstance().getGameMap().getChildren().add(effect);
                deleteEffect.play();
            }
        }));
        addEffect.play();
    }
}

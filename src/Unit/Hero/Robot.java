package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Type.SpecialEffect;
import Utils.UnitState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Robot extends BaseHero implements SpecialEffect {
    public Robot() {
        super(0, 400000, 100, 60, 10, 0, 500, "Robot", 2.8, "Robot/idle.gif", 1000, 1600);
        getImageView().setFitWidth(300);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(120);
    }
    @Override
    public BaseHero clone() {
        return new Robot();
    }

    @Override
    public void showEffect(BaseUnit target) {
        ImageView effect = new ImageView(new Image("Robot/effect.gif"));
        effect.setFitWidth(300);
        effect.setFitHeight(120);

        Timeline deleteEffect = new Timeline(new KeyFrame(Duration.millis(600), e -> {
            GameController.getInstance().getGameMap().getChildren().remove(effect);
        }));
        Timeline addEffect = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            if(getState() != UnitState.DEAD) {
                effect.setTranslateX(target.getImageView().getTranslateX() + 100);
                effect.setTranslateY(target.getImageView().getTranslateY() + 10);
                GameController.getInstance().getGameMap().getChildren().add(effect);
                deleteEffect.play();
            }
        }));
        addEffect.play();
    }

    @Override
    public void destroyed() {
        super.destroyed();
        getImageView().setImage(new Image("Robot/dead.gif"));
        Timeline delete = new Timeline(new KeyFrame(Duration.millis(2000), e -> {
            GameController.getInstance().getGameMap().getChildren().remove(getImageView());
        }));
        delete.play();
    }
}

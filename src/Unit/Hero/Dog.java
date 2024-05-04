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

public class Dog extends BaseHero implements SpecialEffect {

    public Dog() {
        super(600, 90, 800, 40, 350, 500, "Dog", 1.5, "Dog/idle.gif", 1000, 1600, 1800);
        getImageView().setFitWidth(200);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(150);
    }
    @Override
    public BaseHero clone() {
        return new Dog();
    }

    @Override
    public void showEffect(BaseUnit target) {
        ImageView effect = new ImageView();
        effect.setFitWidth(150);
        effect.setPreserveRatio(true);

        Timeline deleteEffect = new Timeline(new KeyFrame(Duration.millis(750), e -> {
            GameController.getInstance().getGameMap().getChildren().remove(effect);
        }));
        Timeline addEffect = new Timeline(new KeyFrame(Duration.millis(1500), e -> {
            if(getState() != UnitState.DEAD) {
                effect.setImage(GameUtils.setImageByPath("Dog/effect.gif"));
                effect.setTranslateX(target.getImageView().getTranslateX() + 120);
                effect.setTranslateY(target.getImageView().getTranslateY() - 5);
                GameController.getInstance().getGameMap().getChildren().add(effect);
                deleteEffect.play();
            }
        }));
        addEffect.play();
    }
}

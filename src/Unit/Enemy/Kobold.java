package Unit.Enemy;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Enemy.BaseEnemy;
import Unit.Type.SpecialEffect;
import Utils.GameUtils;
import Utils.UnitState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Kobold extends BaseEnemy implements SpecialEffect {
    public Kobold() {
        super(200, 40, 200, 60, "Kobold", 2, "Kobold/idle.gif",800, 1600, 1400);
        getImageView().setFitWidth(150);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(135);
    }

    @Override
    public void showEffect(BaseUnit target) {
        ImageView effect = new ImageView();
        effect.setScaleX(-1);
        effect.setFitWidth(200);
        effect.setFitHeight(120);

        Timeline deleteEffect = new Timeline(new KeyFrame(Duration.millis(1400), e -> {
            GameController.getInstance().getGameMap().getChildren().remove(effect);
        }));
        Timeline addEffect = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            effect.setImage(GameUtils.setImageByPath("Kobold/effect.gif"));
            if(getState() != UnitState.DEAD) {
                effect.setTranslateX(target.getImageView().getTranslateX() - 190);
                effect.setTranslateY(target.getImageView().getTranslateY() + 10);
                GameController.getInstance().getGameMap().getChildren().add(effect);
                deleteEffect.play();
            }
        }));
        addEffect.play();
    }
}

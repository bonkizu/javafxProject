package Unit.Enemy;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Enemy.BaseEnemy;
import Unit.Type.SpecialEffect;
import Utils.UnitState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Kobold extends BaseEnemy implements SpecialEffect {
    public Kobold() {
        super(10000, 40, 10000, 60, 10, 0, "Kobold", 2, "Kobold/idle.gif",1000, 1600);
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
            effect.setImage(new Image("Kobold/effect.gif"));
            if(getState() != UnitState.DEAD) {
                effect.setTranslateX(target.getImageView().getTranslateX() - 190);
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
        getImageView().setImage(new Image("Kobold/dead.gif"));
        Timeline delete = new Timeline(new KeyFrame(Duration.millis(2000), e -> {
            GameController.getInstance().getGameMap().getChildren().remove(getImageView());
        }));
        delete.play();
    }
}

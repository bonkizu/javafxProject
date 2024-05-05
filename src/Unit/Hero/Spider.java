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
        super(200, 50, 300, 60, 150, 500, "Spider", 0.9, "Spider/idle.gif", 500, 1600, 1800);
        getImageView().setFitWidth(120);
        getImageView().setPreserveRatio(true);
        getImageView().setTranslateY(-10);
        getImageView().setTranslateX(15);
    }

    @Override
    public BaseHero clone() {
        Spider spider = new Spider();
        spider.getImageView().setFitWidth(150);
        spider.getImageView().setPreserveRatio(true);
        spider.getImageView().setTranslateY(135);
        return spider;
    }
}

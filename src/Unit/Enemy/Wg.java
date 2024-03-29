package Unit.Enemy;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Hero.BaseHero;
import Unit.Type.Attackable;
import Utils.GameUtils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Wg extends BaseEnemy {
    public Wg() {
        super(10, 10, 1000, 50, 10, "Ui", 1.5, "wg.gif");
        initializeSound();
    }

    public void initializeSound() {
        String path = ClassLoader.getSystemResource("rbds.mp3").toString();
        Media sound = new Media(path);
        AudioClip mediaPlayer;
        mediaPlayer = new AudioClip(sound.getSource());
        mediaPlayer.play();
    }

    @Override
    public void attack(BaseUnit target) {
        if(target instanceof BaseHero hero) {
            attacking = new Timeline(new KeyFrame(Duration.millis(100), e-> {
                target.setHp(target.getHp() - getAttack());
                System.out.println(target.getHp() + target.getName());
                if(target.getHp() <= 0) {
                    System.out.println(GameController.getInstance().getHeroes().size());
                    System.out.println("ต้อง move");
                    hasTarget = false;
                    move();
                    attacking.stop();
                }
            }));
            attacking.setCycleCount(Animation.INDEFINITE);
            attacking.play();
        }

    }
}



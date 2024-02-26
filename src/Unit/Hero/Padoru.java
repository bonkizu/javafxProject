package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Enemy.BaseEnemy;
import Unit.Type.Attackable;
import Utils.GameUtils;
import Utils.UnitState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Padoru extends BaseHero implements Attackable {

    private Timeline checking;
    private boolean hasTarget = false;
    private Thread attacking;

    public Padoru() {
        super(10, 10, 100, 40, 10, "Padoru", 1.5, "padoru.gif");
        initializeChecking();
    }

    private void initializeChecking() {
        checking = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if(getHp() <= 0) {
                if(attacking != null) {
                    attacking.interrupt();
                }
                stopMoving();
                hasTarget = false;
                checking.stop();
                destroyed();
            }
            if(!hasTarget) {
                for(BaseEnemy enemy : GameController.getInstance().getEnemies()) {
                    if (GameUtils.inRange(this, enemy)) {
                        stopMoving();
                        hasTarget = true;
                        attack(enemy);
                    }
                }
            }
        }));
        checking.setCycleCount(Timeline.INDEFINITE);
        checking.play();
    }

    private void destroyed() {
        GameController.getInstance().getEnemies().remove(this);
        GameController.getInstance().getGameMap().getChildren().remove(getImageView());
    }

    @Override
    public void check() {
        checking.play();
    }

    @Override
    public void attack(BaseUnit target) {
        if(target instanceof BaseEnemy enemy) {
            attacking = new Thread(() -> {
                while (target.getHp() > 0) {
                    target.setHp(target.getHp() - getAttack());
                    try {
                        System.out.println(target.getHp());
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                hasTarget = false;
                move();
            });
        }
        attacking.start();
    }

}

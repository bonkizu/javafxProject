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

public class Padoru extends BaseHero {

    private Timeline checking;
    private boolean hasTarget = false;
    private Thread attacking;

    public Padoru() {
        super(0, 10, 100, 40, 10, "Padoru", 1.5, "padoru.gif");
        super.initializeChecking();
    }
    @Override
    public void attack(BaseUnit target) {
        if(target instanceof BaseEnemy enemy) {
            attacking = new Thread(() -> {
                while (target.getHp() > 0) {
                    target.setHp(target.getHp() - getAttack());
                    try {
//                        System.out.println(target.getHp());
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

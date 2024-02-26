package Unit.Hero;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Enemy.BaseEnemy;
import Unit.Type.Attackable;
import Utils.GameUtils;
import Utils.UnitState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Padoru extends BaseHero {

    public Padoru() {
        super(10, 10, 1000, 40, 10, "Padoru", 1.5, "padoru.gif");

    }
    @Override
    public void attack(BaseUnit target) {
        if(target instanceof BaseEnemy enemy) {
            attacking = new Timeline(new KeyFrame(Duration.millis(10), e-> {
                target.setHp(target.getHp() - getAttack());
                System.out.println(target.getHp() + target.getName());
                if(target.getHp() <= 0) {
                    System.out.println(GameController.getInstance().getEnemies().size());
                    System.out.println("ต้อง move");
                    hasTarget = false;
                    move();
                    attacking.stop();
                }
            }));
            attacking.setCycleCount(Animation.INDEFINITE);
            attacking.play();
//        if(target instanceof BaseEnemy enemy) {
//            attacking = new Thread(() -> {
//                while (target.getHp() > 0) {
//                    target.setHp(target.getHp() - getAttack());
//                    try {
////                        System.out.println(target.getHp());
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                hasTarget = false;
//                move();
//                attacking.interrupt();
//            });
//        }
//        attacking.start();
    }

}
}

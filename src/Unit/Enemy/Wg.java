package Unit.Enemy;

import Game.GameController;
import Unit.BaseUnit;
import Unit.Hero.BaseHero;
import Unit.Type.Attackable;
import Utils.GameUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Wg extends BaseEnemy implements Attackable {

    private Timeline checking;
    public Wg() {
        super(10, 10, 100, 50, 10, "Ui", 1.5, "wg.gif");
        super.initializeChecking();
    }

    @Override
    public void attack(BaseUnit target) {
        if(target instanceof BaseHero hero) {
            this.attacking = new Thread(() -> {
                while (target.getHp() > 0) {
                    target.setHp(target.getHp() - getAttack());
                    try {
                        System.out.println(target.getHp() + target.getName());
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(GameController.getInstance().getHeroes().size());
                System.out.println("ต้อง move");
                hasTarget = false;
                move();
            });
        }
        attacking.start();
    }

}

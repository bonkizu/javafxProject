package Unit.Enemy;

import Unit.BaseUnit;

public class EnemyTower extends BaseEnemy {
    public EnemyTower() {
        super(100, 100, 10000, 0, 1, 0, "EnemyTower", 0, "enemyTower.gif", 0, 0);
        getImageView().setFitWidth(300);
        getImageView().setPreserveRatio(true);
    }
}

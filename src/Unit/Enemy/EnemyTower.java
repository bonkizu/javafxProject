package Unit.Enemy;

import Unit.BaseUnit;

public class EnemyTower extends BaseEnemy {
    public EnemyTower() {
        super(100, 100, 5000, 0, 1, "EnemyTower", 200, "base.png");
    }

    @Override
    public void attack(BaseUnit target) {
        //Do nothing.
    }
}

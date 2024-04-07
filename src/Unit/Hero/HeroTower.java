package Unit.Hero;

import Unit.BaseUnit;

public class HeroTower extends BaseHero {
    public HeroTower() {
        super(100, 1000, 10000, 0, 1, 0, 0, "HeroTower", 200, "base.png");
        getImageView().setFitWidth(300);
        getImageView().setPreserveRatio(true);
    }

    @Override
    public void attack(BaseUnit target) {
        return;
    }

    @Override
    public BaseHero clone() {
        return null;
    }
}

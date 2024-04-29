package Unit.Hero;

import Unit.BaseUnit;

public class HeroTower extends BaseHero {
    public HeroTower() {
        super(1000, 1000, 10000, 0, 1, 0, 0, "HeroTower", 100, "heroTower.gif", 1000, 1600);
        getImageView().setFitWidth(300);
        getImageView().setPreserveRatio(true);
    }

    @Override
    public BaseHero clone() {
        return null;
    }
}

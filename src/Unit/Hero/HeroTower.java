package Unit.Hero;

import Unit.BaseUnit;

public class HeroTower extends BaseHero {
    public HeroTower() {
        super(0, 100, 5000, 0, 0, 0, "HeroTower", 1, "HeroTower/idle.gif", 1, 1, 1);
        getImageView().setFitWidth(300);
        getImageView().setPreserveRatio(true);
    }

    @Override
    public BaseHero clone() {
        return null;
    }
}

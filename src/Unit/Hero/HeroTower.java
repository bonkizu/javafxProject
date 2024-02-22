package Unit.Hero;

public class HeroTower extends BaseHero {
    public HeroTower() {
        super(100, 1000, 1000, 0, 1, "HeroTower", 200, "base.png");
        getImageView().setFitWidth(300);
        getImageView().setPreserveRatio(true);
    }
}

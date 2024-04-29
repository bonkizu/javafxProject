package Unit.Enemy;

public class DarkSpider extends BaseEnemy {

    public DarkSpider() {
        super(10000, 1000, 10000, 20, 1, 0, "Golem", 10, "Spider/idle.gif", 1000, 1600);
        getImageView().setFitWidth(200);
        getImageView().setPreserveRatio(true);
    }



}

package Utils;

import Unit.BaseUnit;

public class GameUtils {
    public static boolean inRange(BaseUnit unit1, BaseUnit unit2) {
        return Math.abs(unit1.getImageView().getTranslateX() - unit2.getImageView().getTranslateX()) < unit1.getRange()*100;
    }
}

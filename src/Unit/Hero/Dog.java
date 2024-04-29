package Unit.Hero;

import Unit.BaseUnit;
import Unit.Type.SpecialEffect;

public class Dog extends BaseHero implements SpecialEffect {

    public Dog() {
        super(1000, 40, 100, 60, 10, 150, 500, "Dog", 1.4, "Dog/idle.gif", 1000, 1600);
    }
    @Override
    public BaseHero clone() {
        return new Dog();
    }

    @Override
    public void showEffect(BaseUnit target) {

    }
}

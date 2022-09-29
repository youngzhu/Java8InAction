package lambdasinaction.chap10;

import java.util.*;

public class Car {

    private Optional<Insurance> insurance;

    public Car() {
        this.insurance = Optional.empty();
    }

    public Optional<Insurance> getInsurance() {
        return insurance;
    }

    public void addInsurance(Insurance insurance) {
        this.insurance = Optional.ofNullable(insurance);
    }
}

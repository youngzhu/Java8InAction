package lambdasinaction.chap10;

import java.util.*;

public class Person {

    private Optional<Car> car;

    public Person() {
        this.car = Optional.empty();
    }

    public Optional<Car> getCar() {
        return car;
    }

    public void addCar(Car car) {
        this.car = Optional.ofNullable(car);
    }
}

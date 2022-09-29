package lambdasinaction.chap10;

import java.util.*;

public class Person {

    private Optional<Car> car;
    private int age;

    public Person() {
        this.car = Optional.empty();
    }

    public Optional<Car> getCar() {
        return car;
    }

    public void addCar(Car car) {
        this.car = Optional.ofNullable(car);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

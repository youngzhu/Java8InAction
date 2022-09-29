package lambdasinaction.chap10;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static java.util.stream.Collectors.toSet;
import static java.util.Optional.*;
import static org.junit.Assert.assertEquals;

public class OptionalMain {

    Insurance pingan = new Insurance("PingAn");
    Insurance cpic = new Insurance("CPIC");

    Car porsche = new Car();
    Car benz = new Car();
    Car bmw = new Car();

    Person zhangsan = new Person();
    Person lisi = new Person();
    Person wang2 = new Person();
    Person wang5 = new Person();

    @Before
    public void init() {
        zhangsan.addCar(porsche);
        // 不加这个会报空指针。。
        // 或者在构造函数中初始化
//        porsche.addInsurance(null);

        lisi.addCar(benz);
        benz.addInsurance(pingan);

        wang5.addCar(bmw);
        bmw.addInsurance(cpic);
    }

    public String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar)
                     .flatMap(Car::getInsurance)
                     .map(Insurance::getName)
                     .orElse("Unknown");
    }

    @Test
    public void testGetCarInsuranceName() {
//        System.out.println(getCarInsuranceName(Optional.of(lisi)));

        assertEquals("Unknown", getCarInsuranceName(of(zhangsan)));
        assertEquals("PingAn", getCarInsuranceName(of(lisi)));
        assertEquals("Unknown", getCarInsuranceName(of(wang2)));
        assertEquals("CPIC", getCarInsuranceName(of(wang5)));
    }

    public Set<String> getCarInsuranceNames(List<Person> persons) {
        return persons.stream()
                      .map(Person::getCar)
                      .map(optCar -> optCar.flatMap(Car::getInsurance))
                      .map(optInsurance -> optInsurance.map(Insurance::getName))
                      .flatMap(Optional::stream)
                      .collect(toSet());
    }

    @Test
    public void testGetCarInsuranceNames() {
        List<Person> personList = Arrays.asList(zhangsan, lisi, wang2, wang5);

        System.out.println(getCarInsuranceNames(personList));
    }

    public String getCarInsuranceName(Optional<Person> person, int minAge) {
        return person.filter(p->p.getAge()>=minAge)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

    @Test
    public void testGetCarInsuranceNameMinAge() {
        int minAge = 35;

        zhangsan.setAge(45);
        lisi.setAge(30);
        wang2.setAge(40);
        wang5.setAge(38);

        assertEquals("Unknown", getCarInsuranceName(of(zhangsan), minAge));
        assertEquals("Unknown", getCarInsuranceName(of(lisi), minAge));
        assertEquals("Unknown", getCarInsuranceName(of(wang2), minAge));
        assertEquals("CPIC", getCarInsuranceName(of(wang5), minAge));
    }
}

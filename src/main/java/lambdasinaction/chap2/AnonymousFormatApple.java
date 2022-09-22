package lambdasinaction.chap2;

import lambdasinaction.common.Apple;

import java.util.Arrays;
import java.util.List;

/**
 * 匿名类实现
 *
 * 缺点：不清晰，不好理解
 * 
 * @author youngzy
 * @since 2022-09-22
 */
public class AnonymousFormatApple {
    interface AppleFormatter {
        String accept(Apple apple);
    }

    static void prettyPrintApple(List<Apple> inventory, AppleFormatter formatter) {
        for (Apple apple : inventory) {
            String output = formatter.accept(apple);
            System.out.println(output);
        }
    }

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80,"green"),
                new Apple(155, "green"),
                new Apple(120, "red"));

        System.out.println("::fancy::");
        prettyPrintApple(inventory, new AppleFormatter() {
            @Override
            public String accept(Apple apple) {
                String characteristic = apple.getWeight() > 150 ? "heavy" : "light";
                return "A " + characteristic + " " + apple.getColor() + " apple";
            }
        });

        System.out.println();
        System.out.println("==simple==");
        prettyPrintApple(inventory, new AppleFormatter() {
            @Override
            public String accept(Apple apple) {
                return "An apple of " + apple.getWeight() + "g";
            }
        });
    }
}

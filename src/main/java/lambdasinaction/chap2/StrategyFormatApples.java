package lambdasinaction.chap2;

import lambdasinaction.common.Apple;

import java.util.Arrays;
import java.util.List;

/**
 * 策略模式实现
 */
public class StrategyFormatApples {
    interface AppleFormatter {
        String accept(Apple apple);
    }

    static void prettyPrintApple(List<Apple> inventory, AppleFormatter formatter) {
        for (Apple apple : inventory) {
            String output = formatter.accept(apple);
            System.out.println(output);
        }
    }

    static class FancyAppleFormatter implements AppleFormatter {
        @Override
        public String accept(Apple apple) {
            String characteristic = apple.getWeight() > 150 ? "heavy" : "light";
            return "A " + characteristic + " " + apple.getColor() + " apple";
        }
    }

    static class SimpleAppleFormatter implements AppleFormatter {

        @Override
        public String accept(Apple apple) {
            return "An apple of " + apple.getWeight() + "g";
        }
    }

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80,"green"),
                new Apple(155, "green"),
                new Apple(120, "red"));

        System.out.println("::fancy::");
        prettyPrintApple(inventory, new FancyAppleFormatter());

        System.out.println();
        System.out.println("==simple==");
        prettyPrintApple(inventory, new SimpleAppleFormatter());
    }
}

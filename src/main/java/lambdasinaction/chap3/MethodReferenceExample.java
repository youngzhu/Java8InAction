package lambdasinaction.chap3;

import lambdasinaction.common.Apple;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 方法引用
 *
 * @author youngzy
 * @since 2022-09-22
 */
public class MethodReferenceExample {
    public static void main(String[] args) {
        sortIgnoreCase1();
        sortIgnoreCase2();

        Function<String, Integer> strToInt1 = (String s) -> Integer.parseInt(s);
        Function<String, Integer> strToInt2 = Integer::parseInt;

        BiPredicate<List<String>, String> contains1 = (list, element) -> list.contains(element);
        BiPredicate<List<String>, String> contains2 = List::contains;

        /********** 构造函数 *********/
        {
            // 无参
            Supplier<Apple> s1 = Apple::new;
            Apple a1 = s1.get();

            Supplier<Apple> s2 = () -> new Apple();
            Apple a2 = s2.get();
        }
        {
            // 一个参数
            Function<Integer, Apple> f1 = Apple::new;
            Apple a1 = f1.apply(110);
            Function<Integer, Apple> f2 = (weight) -> new Apple(weight);
            Apple a2 = f2.apply(110);

            List<Integer> weights = Arrays.asList(110, 80, 150, 210);
            List<Apple> apples = map(weights, Apple::new);
            for (Apple apple : apples) {
                System.out.println(apple);
            }
        }
        {
            // 两个参数
            BiFunction<Integer, String, Apple> f1 = Apple::new;
            Apple a1 = f1.apply(110, "green");
            BiFunction<Integer, String, Apple> f2 = (weight, color) -> new Apple(weight, color);
            Apple a2 = f2.apply(110, "green");
        }
        {
            // 三个构造参数
            Color color = new Color(64, 128, 128);
            System.out.println(color);
            // 需要自定义一个函数接口
            TriFunction<Integer, Integer, Integer, Color> f1 = Color::new;
            Color c1 = f1.apply(64, 128, 128);
            System.out.println(c1);
            TriFunction<Integer, Integer, Integer, Color> f2 = (r, g, b) -> new Color(r, g, b);
            Color c2 = f2.apply(64, 128, 128);
            System.out.println(c2);
        }

        System.out.println(giveMeFruit("banana", 50));
        System.out.println(giveMeFruit("Orange", 500));
//        System.out.println(giveMeFruit("cherry", 55));

    }

    interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }

    /**
     *
     * @param weights - 不同的苹果重量
     * @param f
     * @return - 不同重量的苹果列表
     */
    static List<Apple> map(List<Integer> weights,
                           Function<Integer, Apple> f) {
        List<Apple> apples = new ArrayList<>();
        for (Integer weight : weights) {
            apples.add(f.apply(weight));
        }
        return apples;
    }

    static void sortIgnoreCase1() {
        List<String> list = Arrays.asList("a", "b", "B", "A");
        list.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
        System.out.println(list);
    }
    static void sortIgnoreCase2() {
        List<String> list = Arrays.asList("a", "b", "B", "A");
        list.sort(String::compareToIgnoreCase);
        System.out.println(list);
    }

    interface Fruit {

    }

    static class Orange implements Fruit {
        private int weight;

        public Orange(int weight) {
            this.weight = weight;
        }

        public String toString() {
            return "Orange{" +
                    "weight=" + weight +
                    '}';
        }
    }

    static class Banana implements Fruit {
        private int weight;

        public Banana(int weight) {
            this.weight = weight;
        }

        public String toString() {
            return "Banana{" +
                    "weight=" + weight +
                    '}';
        }
    }

    static Map<String, Function<Integer, Fruit>> fruitMap = new HashMap<>();
    static {
        fruitMap.put("orange", Orange::new);
        fruitMap.put("banana", Banana::new);
    }

    static Fruit giveMeFruit(String fruit, Integer weight) {
        return fruitMap.get(fruit.toLowerCase()).apply(weight);
    }
}

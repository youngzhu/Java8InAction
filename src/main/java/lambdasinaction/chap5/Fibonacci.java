package lambdasinaction.chap5;

import java.util.stream.Stream;

/**
 * 斐波纳契数列
 *
 * @author youngzy
 * @since 2022-09-23
 */
public class Fibonacci {
    public static void main(String[] args) {
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0]+t[1]})
                .limit(10)
                .map(t -> t[0])
                .forEach(System.out::println);
    }
}

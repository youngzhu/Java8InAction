package lambdasinaction.chap5;

import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 斐波纳契数列
 *
 * @author youngzy
 * @since 2022-09-23
 */
public class Fibonacci {
    public static void main(String[] args) {
        List<Integer> fib1 = viaIterate();
        System.out.println(fib1);

        List<Integer> fib2 = viaGenerate();
        System.out.println(fib2);
    }

    static List<Integer> viaIterate() {
        return Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0]+t[1]})
                .limit(10)
                .map(t -> t[0])
//                .forEach(System.out::println);
                .collect(Collectors.toList());
    }

    static List<Integer> viaGenerate() {
        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;
            @Override
            public int getAsInt() {
                int oldPre = this.previous;
                int next = this.previous + this.current;
                this.previous = this.current;
                this.current = next;
                return oldPre;
            }
        };

        return IntStream.generate(fib)
                .limit(10)
//                .forEach(System.out::println);
                .boxed()
                .collect(Collectors.toList());
    }
}

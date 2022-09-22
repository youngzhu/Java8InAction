package lambdasinaction.chap3;

/**
 * 函数式接口：只定义了一个抽象方法的接口
 *
 * @author youngzy
 * @since 2022-09-22
 */
public class FunctionInterface {
    public static void main(String[] args) {
        // 匿名类
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world 1");
            }
        };

        Runnable r2 = () -> System.out.println("Hello World 2");

        process(r1);
        process(r2);
        process(() -> System.out.println("hello world 3"));
    }

    static void process(Runnable r) {
        r.run();
    }
}

package lambdasinaction.chap11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author youngzy
 * @since 2022-09-30
 */
public class MyTestMain {
    final static List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy"));

    final static Executor executor = Executors.newFixedThreadPool(Math.min(shops.size()+1, 100),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                }
            });

    public static void main(String[] args) {
        String product = "myPhone27S";

        System.out.printf("%d cores ", Runtime.getRuntime().availableProcessors());
        test("sequential", () -> findPrices1(product));
        test("parallel", () -> findPrices2(product));
        test("CompletableFuture", () -> findPrices3(product));
        test("fixed thread pool", () -> findPrices4(product));
    }

    static void test(String msg, Supplier<List<String>> supplier) {
        System.out.println("===" + msg + "===");
        long start = System.nanoTime();
        System.out.println(supplier.get());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + "ms");
    }

    static List<String> findPrices1(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f",
                        shop.getName(),
                        shop.calculatePrice(product)))
                .collect(Collectors.toList());
    }

    // 并行流
    static List<String> findPrices2(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f",
                        shop.getName(),
                        shop.calculatePrice(product)))
                .collect(Collectors.toList());
    }

    // CompletableFuture
    static List<String> findPrices3(String product) {
        List<CompletableFuture<String>> futures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getName() + " price is " + shop.calculatePrice(product)
                )).collect(Collectors.toList());

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    // fixed thread pool
    static List<String> findPrices4(String product) {
        List<CompletableFuture<String>> futures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getName() + " price is " + shop.calculatePrice(product),
                        executor
                )).collect(Collectors.toList());

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}

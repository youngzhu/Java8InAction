package lambdasinaction.chap4;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 中间操作
 *
 * 利用中间操作的延迟性，对数据操作（计算）进行了优化
 * 例如，并没有打印出所有菜名，只打印了满足条件的3个
 *
 * @author youngzy
 * @since 2022-09-23
 */
public class IntermediateOperation {
    public static void main(String[] args) {
        List<String> names = Dish.menu.stream()
                .filter(d -> {
                    System.out.println("filtering: " + d.getName());
                    return d.getCalories() > 300;
                })
                .map(dish -> {
                    System.out.println("mapping: " + dish.getName());
                    return dish.getName();
                })
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(names);
    }
}

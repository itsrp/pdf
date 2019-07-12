package temp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Filter01 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");

        boolean b = list1.stream().allMatch(list::contains);
        System.out.println(b);

    }
}

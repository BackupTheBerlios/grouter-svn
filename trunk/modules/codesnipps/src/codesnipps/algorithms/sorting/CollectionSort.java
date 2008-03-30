package codesnipps.algorithms.sorting;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Example taken from http://java.sun.com/docs/books/tutorial/collections/algorithms/index.html # Fast: It is guaranteed
 * to run in n log(n) time and runs substantially faster on nearly sorted lists. Empirical tests showed it to be as fast
 * as a highly optimized quicksort. Quicksort is generally regarded to be faster than merge sort but isn't stable and
 * doesn't guarantee n log(n) performance. # Stable: It doesn't reorder equal elements. This is important if you sort
 * the same list repeatedly on different attributes. If a user of a mail program sorts the in-box by mailing date and
 * then sorts it by sender, the user naturally expects that the now-contiguous list of messages from a given sender will
 * (still) be sorted by mailing date. This is guaranteed only if the second sort was stable.
 * 
 * @author gepo
 */
public class CollectionSort
{
    static String[] listarr =
    { "hello", "world", "how", "are", "you" };

    static List<String> strList = Arrays.asList(listarr);

    private static void doSort()
    {
        Collections.sort(strList);
        System.out.println(strList);
    }

    public static void main(String args[])
    {
        doSort();
    }
}

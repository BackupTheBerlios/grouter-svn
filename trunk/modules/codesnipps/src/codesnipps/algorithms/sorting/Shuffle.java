package codesnipps.algorithms.sorting;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Date 
 * Author 
 * $Id 
 * Revision
 * @author gepo
 *
 */
public class Shuffle
{
    static String[] listarr =
    { "A", "blue", "code", "devil" };

    static List<String> strList = Arrays.asList(listarr);

    private static void doSort()
    {
        Collections.sort(strList);
        System.out.println(strList);
        Collections.shuffle(strList);
        System.out.println(strList);
        Collections.sort(strList);
        System.out.println(strList);
    }

    public static void main(String args[])
    {
        doSort();
    }
}

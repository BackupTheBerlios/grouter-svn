package codesnipps.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayOperations
{
    static int[] arr1 =
    { 3, 2, 4, 55, 33, 23, 45, 78 };

    static int[] arr2 =
    { 1, 2, 4, 55, 33, 23, 45, 78 };

    static String[][] tictac1 = 
    {
    { "X", "O", "X" },
    { "X", "O", "X" },
    { "X", "O", "X" }, };

    static String[][] tictac2 =
    {
    { "X", "O", "X" },
    { "X", "O", "X" },
    { "X", "O", "O" }, };

    private static void doStuff()
    {
        if (Arrays.equals(arr1, arr2))
        {
            System.out.println("Arrays are equal" + Arrays.toString(arr1));
        } else
        {
            System.out.println("Arrays are not equal" + Arrays.toString(arr1));
        }
        System.out.println("55 is located at array position "
                + Arrays.binarySearch(arr1, 55));

        //Compare multi dimensional arrays
        System.out.println("Are tic tac toes equal? : "
                + Arrays.deepEquals(tictac2, tictac1));

        System.out.println("Are tic tac toes equal? : "
                + Arrays.deepEquals(tictac1, tictac1));
        System.out.println("Tic tac output : " + Arrays.deepToString(tictac1));
    }

    public static void main(String[] args)
    {
        ArrayOperations.doStuff();

    }
}

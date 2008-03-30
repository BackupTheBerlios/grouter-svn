package codesnipps.collections.sets;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * From http://java.sun.com/developer/JDCTechTips/2002/tt1105.html#1
 */
public class SetDemo
{
    static final int MIN = 1;
    static final int MAX = 10;

    public static void main(String args[])
    {
        Set sethash = new HashSet();
        for (int i = MAX; i >= MIN; i--)
        {
            Integer item = new Integer(i * i);
            sethash.add(item);
            System.out.println(item);
        }
        System.out.println("HashSet = " + sethash);

        Set setlink = new LinkedHashSet();
        for (int i = MAX; i >= MIN; i--)
        {
            setlink.add(new Integer(i * i));
        }
        System.out.println("LinkedHashSet = " + setlink);

        Set settree = new TreeSet();
        for (int i = MAX; i >= MIN; i--)
        {
            settree.add(new Integer(i * i));
        }
        System.out.println("TreeSet = " + settree);
    }
}
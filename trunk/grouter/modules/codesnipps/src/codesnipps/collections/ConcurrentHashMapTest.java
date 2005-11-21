package codesnipps.collections;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest
{
    static Map mp = new ConcurrentHashMap();
    
    private static void doStuff()
    {
        mp.put("key1","value1");
        mp.put("key2","value2");
        Set entries = mp.entrySet();
        for (Iterator iter = entries.iterator(); iter.hasNext();)
        {
            ConcurrentHashMap element = (ConcurrentHashMap) iter.next();
            System.out.println(element.toString());
        }

    }
    
    public static void main(String[] args)
    {
        doStuff();
    }

}

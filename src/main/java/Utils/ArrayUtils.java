package Utils;

import java.util.Map;

public class ArrayUtils {

    /**
     * check if arrays are deep equal
     * @param a
     * @param a2
     * @return
     */
    public static boolean arrayEquals(Object[] a, Object[] a2){
        if(a==a2)
            return true;
        if(a==null || a2==null)
            return false;
        else{
            boolean eq=false;
            Map<Object,Object> map1= org.apache.commons.lang3.ArrayUtils.toMap(a);
            Map<Object,Object> map2= org.apache.commons.lang3.ArrayUtils.toMap(a2);

            for(Object x:a)
            {
                if(map2.get(x)==map1.get(x))
                    eq=true;
                else {eq=false; return eq;}
            }
            return eq;
        }
    }


}

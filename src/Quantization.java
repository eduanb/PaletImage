import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by Eduan on 2015-03-21.
 */
public abstract class Quantization {
    public abstract LinkedHashMap PerformQuantization(BufferedImage bufferedImage, int palletSize);
    protected List SortedMap(Map map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });
        return list;
    }
}

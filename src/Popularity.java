import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eduan on 2015-03-21.
 */
public class Popularity extends Quantization {
    @Override
    public LinkedHashMap PerformQuantization(BufferedImage bufferedImage, int paletSize) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                if (map.get(bufferedImage.getRGB(x, y)) == null)
                    map.put(bufferedImage.getRGB(x, y), 1);
                else
                    map.put(bufferedImage.getRGB(x, y), map.get(bufferedImage.getRGB(x, y)) + 1);
            }
        }

        List sortedList = SortedMap(map);
        //We are only interested in first n elements
        if(sortedList.size() > paletSize)
            sortedList.subList(paletSize, sortedList.size()).clear();
        byte i = 0;
        LinkedHashMap<Integer,Byte> paletHash = new LinkedHashMap<>();
        for(Object o : sortedList)
        {
            Map.Entry<Integer,Integer> entry = (Map.Entry<Integer, Integer>) o;
            paletHash.put(entry.getKey(),i);
            i++;
        }
        return paletHash;
    }
}

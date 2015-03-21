import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Eduan on 2015-03-21.
 */
public class Truncation extends Dithering {
    @Override
    public byte[][] PerformDithering(BufferedImage bufferedImage, LinkedHashMap<Integer,Byte> palletHash)
    {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        byte[][] colorPointers = new byte[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++)
            {
                int rgb = bufferedImage.getRGB(x,y);
                Byte hash = palletHash.get(rgb);
                if(palletHash.get(rgb) != null)
                {
                    //Yeah its in list simple case
                    colorPointers[x][y] = hash;
                }
                else
                {
                    Byte bestPos = 0;
                    double bestDist = Double.MAX_VALUE;
                    for (Map.Entry<Integer,Byte> entry : palletHash.entrySet())
                    {
                        double dist = Distance(new Color(rgb), new Color(entry.getKey()));
                        if(dist < bestDist)
                        {
                            bestDist = dist;
                            bestPos = entry.getValue();
                        }
                    }
                    colorPointers[x][y] = bestPos;

                }
            }
        }
        return colorPointers;
    }
}

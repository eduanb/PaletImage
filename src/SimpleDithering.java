import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Eduan on 2015-03-21.
 */
public class SimpleDithering extends Dithering {
    @Override
    public byte[][] PerformDithering(BufferedImage bufferedImage, LinkedHashMap<Integer, Byte> palletHash) {
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
                    Map.Entry<Integer,Byte> bestPos = null;
                    double bestDist = Double.MAX_VALUE;
                    for (Map.Entry<Integer,Byte> entry : palletHash.entrySet())
                    {
                        double dist = Distance(new Color(rgb), new Color(entry.getKey()));
                        if(dist < bestDist)
                        {
                            bestDist = dist;
                            bestPos = entry;
                        }
                    }
                    if(x < width -1) //need to move error on
                    {
                        Color yi = new Color(bestPos.getKey());
                        Color xi = new Color(rgb);
                        int er = xi.getRed() - yi.getRed();
                        int eb = xi.getBlue() - yi.getBlue();
                        int eg = xi.getGreen() - yi.getGreen();
                        Color next = new Color(bufferedImage.getRGB(x + 1,y));
                        int nr = Math.max(0, Math.min(255, next.getRed() + er));
                        int ng = Math.max(0, Math.min(255, next.getGreen() + eg));
                        int nb = Math.max(0, Math.min(255, next.getBlue() + eb));
                        Color tempCol = new Color(nr,ng,nb);
                        bufferedImage.setRGB(x+1, y, tempCol.getRGB());
                    }
                    colorPointers[x][y] = bestPos.getValue();
                }
            }
        }
        return colorPointers;
    }
}

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created by Eduan on 2015-03-21.
 */
public class MedianCut extends Quantization {
    @Override
    public LinkedHashMap PerformQuantization(BufferedImage bufferedImage, int palletSize) {
        this.palletSize = palletSize;
        List<Color> originalColors = new LinkedList<>();
        for(int x = 0; x < bufferedImage.getWidth(); x++)
        {
            for(int y = 0; y < bufferedImage.getHeight(); y++)
            {
                originalColors.add(new Color(bufferedImage.getRGB(x,y)));
            }
        }
        PerformMedianCut( originalColors, 0);
        return palletHash;
    }

    int palletSize;
    LinkedHashMap<Integer,Byte> palletHash = new LinkedHashMap<>();
    byte pos = 0;
    private void PerformMedianCut( List<Color> originalColors, int n)
    {
        //TODO: Check case where less than 255 colors
        //TODO: Think about this stopping condition
        if(n == palletSize)
        {
            palletHash.put(findAverage(originalColors).getRGB(), pos++);
        }
        else
        {
            int splitCol = findColorToSplitOn(originalColors);
            List<List<Color>> splitColors = split(originalColors,splitCol);
            n++;
            PerformMedianCut(splitColors.get(0), n);
            PerformMedianCut(splitColors.get(1), n);
        }

    }

    private int findColorToSplitOn( List<Color> originalColors)
    {
        int maxR = 0, maxG = 0, maxB = 0;
        int minR = 255, minG = 255, minB = 255;
        for(Color c : originalColors)
        {
            maxR = Math.max(c.getRed(),maxR);
            maxG = Math.max(c.getGreen(),maxG);
            maxB = Math.max(c.getBlue(),maxB);

            minR = Math.min(c.getRed(),minR);
            minG = Math.min(c.getGreen(),minG);
            minB = Math.min(c.getBlue(), minB);
        }
        int distR = maxR - minR;
        int distG = maxG - minG;
        int distB = maxB - minB;
        return findLargestIndex(distR,distG,distB);
    }

    private int findLargestIndex(int ... colors)
    {
        int result = -1;
        int largest = Integer.MIN_VALUE;
        for(int i = 0; i < colors.length; i++)
        {
            if(colors[i] > largest) {
                result = i;
                largest = colors[i];
            }
        }
        return result;
    }

    private  List<List<Color>> split( List<Color> originalColors, int dimensionToSplitOn)
    {
        //TODO:
        return null;
    }

    private Color findAverage(List<Color> originalColors)
    {
        int R = 0, G = 0, B = 0;
        for(Color c : originalColors)
        {
            R += c.getRed();
            G += c.getGreen();
            B += c.getBlue();
        }
        R = R / originalColors.size();
        G = G / originalColors.size();
        B = B / originalColors.size();
        return new Color(R,G,B);
    }
}

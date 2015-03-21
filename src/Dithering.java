import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

/**
 * Created by Eduan on 2015-03-21.
 */
public abstract class Dithering {
    public abstract byte[][] PerformDithering( BufferedImage bufferedImage, LinkedHashMap<Integer, Byte> palletHash);
    protected double Distance(Color a, Color b)
    {
        double blue = a.getBlue() - b.getBlue();
        double red = a.getRed() - b.getRed();
        double green = a.getGreen() - b.getGreen();

        return Math.sqrt((blue * blue) + (red * red) + (green * green));
    }

}

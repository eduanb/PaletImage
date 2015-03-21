import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

/**
 * Created by Eduan on 2015-03-21.
 */
public class SimpleDithering extends Dithering {
    @Override
    public byte[][] PerformDithering(BufferedImage bufferedImage, LinkedHashMap<Integer, Byte> palletHash) {
        return new byte[0][];
    }
}

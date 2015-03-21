import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
/**
 * Created by Eduan on 2015-03-17.
 */
public class ImageConverter {
    public void ConvertAndSaveBMP(File bmpImage, int paletSize, Quantization quantization, Dithering dither)
    {
        try {
        long start = System.currentTimeMillis();


            BufferedImage bufferedImage  = ImageIO.read(bmpImage);

            LinkedHashMap<Integer,Byte> palletHash = quantization.PerformQuantization(bufferedImage, paletSize);
            byte[][] colorPointers = dither.PerformDithering(bufferedImage,palletHash);
            PrintPallet(palletHash);
            PrintImage(colorPointers, bufferedImage.getWidth(), bufferedImage.getHeight());
            long end = System.currentTimeMillis();
            long timeInMillis = end - start;
            System.out.println("Total time for converting and saving:" +timeInMillis + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void PrintImage( byte[][] colorPointers, int width, int height)
    {
        String lineSeparator=System.getProperty("line.separator");
        StringJoiner yjoiner = new StringJoiner(lineSeparator);
        yjoiner.add(width + "," + height);
        for (int y = 0; y < height; y++) {
            StringJoiner xjoiner = new StringJoiner(",");
            for (int x = 0; x <width; x++) {
                xjoiner.add("" + colorPointers[x][y]);
            }
            yjoiner.add(xjoiner.toString());
        }
        printFile("image.pbi",yjoiner.toString());
    }

    private void PrintPallet(LinkedHashMap<Integer,Byte> palletHash)
    {
        StringJoiner joiner = new StringJoiner(",");
        for (Map.Entry<Integer,Byte> entry : palletHash.entrySet()) {
           joiner.add(entry.getKey().toString());
        }
        printFile("pallet.pl",joiner.toString());
    }

    private void printFile(String FileName, String content)
    {
        PrintWriter writer;
        try
        {
            writer = new PrintWriter(FileName, "UTF-8");
            writer.print(content);
            writer.close();
        }
        catch (FileNotFoundException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }
}

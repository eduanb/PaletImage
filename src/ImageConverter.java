import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
/**
 * Created by Eduan on 2015-03-17.
 */
public class ImageConverter {
    public ImageConverter(File bmpImage)
    {
        this.bmpImage = bmpImage;
        try {
            this.bufferedImage  = ImageIO.read(bmpImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Popularity(paletSize);
        Truncation();
        PrintPalet();
        PrintImage();
    }
    int paletSize = 255;
    File bmpImage;
    BufferedImage bufferedImage;
    List<Color> paletList;
    LinkedHashMap<Integer,Byte> paletHash;
    byte[][] colorPointers;

    private void PrintImage()
    {
        String lineSeparator=System.getProperty("line.separator");
        StringJoiner yjoiner = new StringJoiner(lineSeparator);
        for (int y = 0; y < bufferedImage.getWidth(); y++) {
            StringJoiner xjoiner = new StringJoiner(",");
            for (int x = 0; x < bufferedImage.getHeight(); x++) {
                xjoiner.add("" + colorPointers[x][y]);
            }
            yjoiner.add(xjoiner.toString());
        }
        printFile("image.img",yjoiner.toString());
    }

    private void PrintPalet()
    {
        StringJoiner joiner = new StringJoiner(",");
        for (Map.Entry<Integer,Byte> entry : paletHash.entrySet()) {
           joiner.add(entry.getKey().toString());
        }
        printFile("palet.pl",joiner.toString());
    }
    private void Popularity(int count)
    {
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
        if(sortedList.size() > count)
            sortedList.subList(count, sortedList.size()).clear();
        byte i = 0;
        paletHash = new LinkedHashMap<>();
        for(Object o : sortedList)
        {
            Map.Entry<Integer,Integer> entry = (Map.Entry<Integer, Integer>) o;
            paletHash.put(entry.getKey(),i);
            i++;
        }
    }

    private void Truncation()
    {
        colorPointers = new byte[bufferedImage.getWidth()][bufferedImage.getHeight()];
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++)
            {
                int rgb = bufferedImage.getRGB(x,y);
                Byte hash = paletHash.get(rgb);
                if(paletHash.get(rgb) != null)
                {
                    //Yeah its in list simple case
                    colorPointers[x][y] = hash;
                }
                else
                {
                    //TODO
                }
            }
        }
    }

    private int unsignedToBytes(byte b) {
        return b & 0xFF;
    }
    private List SortedMap(Map map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });
        return list;
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

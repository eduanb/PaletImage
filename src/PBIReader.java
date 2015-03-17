import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Eduan on 2015-03-17.
 */
public class PBIReader {
    byte[][] image = null;
    Color[] pallet = null;
    File pbiFile;
    File plFile;
    BufferedImage result;
    public BufferedImage getBufferedImage(File pbi, File pl)
    {
        pbiFile = pbi;
        plFile = pl;
        readPallet();
        readImage();
        return result;
    }
    private void readPallet()
    {
        pallet = new Color[255];

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(plFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int i = 0;
            for(String palletColor : line.split(","))
            {
                int col = Integer.parseInt(palletColor);
                pallet[i++] = new Color(col);
            }

            String everything = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    public static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }
    private void readImage()
    {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(pbiFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int width = Integer.parseInt(line.substring(0,line.indexOf(','))) + 1;
            int height = Integer.parseInt(line.substring(line.indexOf(',')+1,line.length())) + 1;
            image = new byte[width][height];
            result = new BufferedImage(width + 1,height + 1,BufferedImage.TYPE_3BYTE_BGR);

            int x = 0, y =0;
            line = br.readLine();
            while (line != null) {
                y++;
                x = 0;
                for(String palletPointer : line.split(","))
                {
                    x++;
                    byte b = Byte.parseByte(palletPointer);
                    image[x][y] = b;
                    Color c = pallet[unsignedToBytes(b)];
                    result.setRGB(x,y,c.getRGB());

                }
                line = br.readLine();
            }
            String everything = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

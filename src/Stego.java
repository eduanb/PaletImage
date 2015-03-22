import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Created by Eduan on 2015-03-22.
 */
public abstract class Stego {
    public abstract void EmbedMessage(File pbi, File pl, String message);

    public abstract String readMessage(File pbi, File pl);

    protected char[] stringToBinary(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.US_ASCII);
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            binary.append(Integer.toBinaryString((int) b));
        }
        return binary.toString().toCharArray();
    }

    protected void printFile(String FileName, String content)
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

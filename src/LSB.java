import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduan on 2015-03-22.
 */
public class LSB extends Stego {
    @Override
    public void EmbedMessage(File pbiFile, File pl, String message) {
        char[] binaryMessage = stringToBinary(message.replaceAll(" ", ""));
        System.out.println("Binary message("+binaryMessage.length+"):" + new String(binaryMessage));
        //--------------------------------------------------
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        String lineSeparator=System.getProperty("line.separator");
        int messageIndex = 0;
        try {
            br = new BufferedReader(new FileReader(pbiFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String line = br.readLine();
            result.append(line);
            line = br.readLine();
            while (line != null) {
                result.append(lineSeparator);
                for(String palletPointer : line.split(","))
                {
                    byte b = Byte.parseByte(palletPointer);
                    if(messageIndex < binaryMessage.length) {
                        char[] binary = Integer.toBinaryString(b).toCharArray();
                        binary[binary.length - 1] = binaryMessage[messageIndex];
                        String temp = new String(binary);
                        b = Byte.parseByte(temp,2);
                        result.append(b);
                        System.out.println(messageIndex);
                        messageIndex++;
                    }
                    else
                    {
                        result.append(b);
                    }
                    result.append(",");

                }
                line = br.readLine();
            }
            printFile(pbiFile.getName(),result.toString());
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

    @Override
    public String readMessage(File pbiFile, File pl) {
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        int messageIndex = 0;
        try {
            br = new BufferedReader(new FileReader(pbiFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String line = br.readLine();
            line = br.readLine();
            while (line != null) {
                for(String palletPointer : line.split(","))
                {
                    byte b = Byte.parseByte(palletPointer);
                    if(messageIndex < 128 * 7) {
                        char[] binary = Integer.toBinaryString(b).toCharArray();
                        result.append(binary[binary.length - 1]);
                        messageIndex++;
                    }
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Binary read Message:"+result.toString());
        return int2str(result.toString());
    }

    public static String int2str( String s ) {
        List<String> ss = getParts(s, 7);
        StringBuilder sb = new StringBuilder();
        for(String str : ss)
        {
            sb.append( (char)Integer.parseInt( str, 2 ) );
        }
        return sb.toString();
    }

    protected static List<String> getParts(String string, int partitionSize) {
        List<String> parts = new ArrayList<String>();
        int len = string.length();
        for (int i=0; i<len; i+=partitionSize)
        {
            parts.add(string.substring(i, Math.min(len, i + partitionSize)));
        }
        return parts;
    }

}

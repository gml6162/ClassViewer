package viewerPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by gej48 on 2017-05-18.
 */
public class ReadFileData {

    public static void main(String[] args) {
        int b = 0;
        StringBuffer buffer = new StringBuffer();
        FileInputStream file = null;
        try {
            file = new FileInputStream(ReadFileData.class.getResource("").getPath()+"/Queue.cpp");
            b = file.read();
            while(b != -1) {
                buffer.append((char)b);
                b = file.read();
            }
            //System.out.print(buffer);
            Parsing parsing = new Parsing(buffer);
        }catch(FileNotFoundException e) {
            System.out.println("Oops : FileNotFoundException");
        }catch(IOException e) {
            System.out.println("Input error");
        }
    }
}

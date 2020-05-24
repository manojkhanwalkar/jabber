package util;

import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.jar.JarFile;

public class JarBase64Util {


    public static String getBase64EncodedStringFromJar(String fileName)
    {
        String str = null;
        try {
            byte[] bytes = Files.toByteArray(new File(fileName));
            str = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;

    }


    public static void getJarFromBase64EncodedString(String fileName, String encodedString)
    {
        byte[] bytes = Base64.getDecoder().decode(encodedString);
        try {
            FileUtils.writeByteArrayToFile(new File(fileName), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {



        String str = getBase64EncodedStringFromJar("/tmp/epi.jar");

        //System.out.println(str);

        getJarFromBase64EncodedString("/tmp/epirestored.jar", str);



        System.out.println("Check /tmp for jar file created");

    }
}

package util;

import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;
import org.xeustechnologies.jcl.JclUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.jar.JarFile;

public class JarUtils {


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

    public static Object loadClassFromJar(String jarname,String jobClassName )
    {
        try {
            JarClassLoader jcl = new JarClassLoader();
        jcl.add(jarname); // Load jar file

        JclObjectFactory factory = JclObjectFactory.getInstance();
// Create object of loaded class
        Object obj = factory.create(jcl, jobClassName);



            return obj;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }


    public static void main(String[] args) throws Exception {



        String str = getBase64EncodedStringFromJar("/tmp/epi.jar");

        //System.out.println(str);

        getJarFromBase64EncodedString("/tmp/epirestored.jar", str);


        String jarName = "/tmp/epirestored.jar";

        {

            String jobClassName = "tmp.Test";
            Object obj = loadClassFromJar(jarName, jobClassName);

            Class c = obj.getClass();

            Method method = c.getMethod("compute");


            System.out.println(method.invoke(obj));

        }

        {

            String jobClassName = "tmp.AnotherTest";
            Object obj = loadClassFromJar(jarName, jobClassName);

            Class c = obj.getClass();

            Method method = c.getMethod("compute");


            System.out.println(method.invoke(obj));

        }



    }
}

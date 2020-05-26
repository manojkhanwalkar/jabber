package worker;

import data.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import static util.JarUtils.getJarFromBase64EncodedString;
import static util.JarUtils.loadClassFromJar;

public class WorkManager {



    public WorkerResponse process(WorkerRequest request) {


        try {
            Thread.sleep(5000);

            String jarName = "/tmp/"+ UUID.randomUUID().toString()+"*.jar";
            getJarFromBase64EncodedString(jarName, request.getBase64Jar());

            String jobClassName = request.getJobClassName();
            Object obj = loadClassFromJar(jarName, jobClassName);
            Class c = obj.getClass();
            Method method = c.getMethod("compute");

            Result result = new Result();
            String res= (String)method.invoke(obj);

            result.setResult(res);

            WorkerResponse response = new WorkerResponse();
            response.setJobStatus(new StatusTuple(request.getJobId(),Status.Complete,result));

            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;

    }
}

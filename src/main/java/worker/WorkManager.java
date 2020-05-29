package worker;

import data.*;
import util.Connection;
import util.JSONUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static util.JarUtils.getJarFromBase64EncodedString;
import static util.JarUtils.loadClassFromJar;

public class WorkManager {



    public void process(WorkerRequest request) {


        CompletableFuture.runAsync(()->{




        try {
            //Thread.sleep(5000);

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

            sendResponse(response);


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        });




    }


    private void sendResponse(WorkerResponse response)
    {


                Connection app = new Connection("https://localhost:8480/");


                app.sendSimple(JSONUtil.toJSON(response), "response");




    }

}

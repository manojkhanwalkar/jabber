package scheduler;

import data.Result;
import data.Status;
import data.SubmitRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import static util.JarUtils.getJarFromBase64EncodedString;
import static util.JarUtils.loadClassFromJar;

public class JobProcessor implements Runnable
{

    SubmitRequest submitRequest;
    String jobId;
    JobCache jobcache;

    public JobProcessor(SubmitRequest submitRequest, String jobId, JobCache jobcache) {
        this.submitRequest = submitRequest;
        this.jobId = jobId;
        this.jobcache = jobcache;
    }

    private void process()
    {
        try {

            Thread.sleep(5000);

            String jarName = "/tmp/"+ UUID.randomUUID().toString()+"*.jar";
            getJarFromBase64EncodedString(jarName, submitRequest.getBase64Jar());

            String jobClassName = submitRequest.getJobClassName();
            Object obj = loadClassFromJar(jarName, jobClassName);
            Class c = obj.getClass();
            Method method = c.getMethod("compute");

            Result result = new Result();
            String res= (String)method.invoke(obj);

            result.setResult(res);
            jobcache.update(submitRequest.getClient(), jobId, Status.Complete,result);


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void run()
    {
            process();
    }


}

package scheduler;

import data.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import static util.JarUtils.getJarFromBase64EncodedString;
import static util.JarUtils.loadClassFromJar;

public class ClientRequestManager {

    JobCache jobcache = new JobCache();

    public SubmitResponse submit(SubmitRequest submitRequest)
    {

        String jobId = UUID.randomUUID().toString();
        SubmitResponse response = new SubmitResponse(submitRequest.getClient(),jobId);

        jobcache.add(submitRequest.getClient(), jobId, Status.Submitted );

        process(submitRequest, jobId);

        return response;

    }

    private void process(SubmitRequest submitRequest, String jobId)
    {
        try {
            String jarName = "/tmp/"+UUID.randomUUID().toString()+"*.jar";
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
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }



    public StatusResponse status(StatusRequest request) {

        StatusResponse response = new StatusResponse();

        request.getJobIds().forEach(id->{

            StatusTuple tuple = jobcache.get(request.getClient(),id );

            response.addStatus(tuple);

        });

        return response;
    }
}

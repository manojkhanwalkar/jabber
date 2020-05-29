package scheduler.persistence;

import data.Result;
import data.SubmitRequest;
import util.JSONUtil;

public class Record {

    public enum Action { Add , Delete};

    Action action;

    public Record(Action action, String clientId, String jobId , String data) {
        this.action = action;
        this.clientId = clientId;
        this.data = data;

        this.jobId = jobId;
    }

    String jobId;

    String clientId;

    String data ;   // json format data .



    public Record()
    {

    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public static Record makeRecord(SubmitRequest submitRequest , String jobId )
    {
        return new Record(Action.Add, submitRequest.getClient(), jobId, JSONUtil.toJSON(submitRequest));
    }


    public static Record makeRecord(String clientId , String jobId , Result result)
    {
        return new Record(Action.Delete, clientId, jobId, JSONUtil.toJSON(result));
    }
}

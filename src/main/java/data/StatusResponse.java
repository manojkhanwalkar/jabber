package data;

import java.util.ArrayList;

public class StatusResponse {


    ArrayList<StatusTuple>  jobStatus = new ArrayList<>();

    public void addStatus(String id , Status status)
    {
        jobStatus.add(new StatusTuple(id,status));
    }

    public void addStatus(StatusTuple tuple)
    {
        jobStatus.add(tuple);
    }




    public ArrayList<StatusTuple> getJobStatus()
    {
        return jobStatus;
    }

    @Override
    public String toString() {
        return "StatusResponse{" +
                "jobStatus=" + jobStatus +
                '}';
    }
}

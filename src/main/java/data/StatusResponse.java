package data;

import java.util.ArrayList;
import java.util.List;

public class StatusResponse {

    static class StatusTuple
    {
        String id;
        Status status;

        public StatusTuple(String id, Status status) {
            this.id = id;
            this.status = status;
        }
    }


    List<StatusTuple>  jobStatus = new ArrayList<>();

    public void addStatus(String id , Status status)
    {
        jobStatus.add(new StatusTuple(id,status));
    }


    public List<StatusTuple> getJobStatus()
    {
        return jobStatus;
    }



}

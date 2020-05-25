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


    ArrayList<StatusTuple>  jobStatus = new ArrayList<>();

    public void addStatus(String id , Status status)
    {
        jobStatus.add(new StatusTuple(id,status));
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

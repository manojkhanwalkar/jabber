package data;

public class WorkerResponse {

    StatusTuple jobStatus;

    public WorkerResponse(StatusTuple jobStatus) {
       this.jobStatus = jobStatus;
    }

    public WorkerResponse() {


    }

    public StatusTuple getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(StatusTuple jobStatus) {
        this.jobStatus = jobStatus;
    }
}



package data;

public class WorkerRequest {

    String base64Jar;
    String jobId;
    String jobClassName;

    public WorkerRequest(String base64Jar, String jobId, String jobClassName) {
        this.base64Jar = base64Jar;
        this.jobId = jobId;
        this.jobClassName = jobClassName;

    }

    public WorkerRequest() {
    }


    public String getBase64Jar() {
        return base64Jar;
    }

    public void setBase64Jar(String base64Jar) {
        this.base64Jar = base64Jar;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }
}

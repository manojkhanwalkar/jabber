package decision.data;

public class DecisionRequest {

    String requestId;
    String workflowId;
    Event event;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "DecisionRequest{" +
                "requestId='" + requestId + '\'' +
                ", workflowId='" + workflowId + '\'' +
                ", event=" + event +
                '}';
    }

    public DecisionRequest(String requestId, String workflowId, Event event) {
        this.requestId = requestId;
        this.workflowId = workflowId;
        this.event = event;
    }

    public DecisionRequest() {
    }
}

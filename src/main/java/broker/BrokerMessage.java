package broker;

public class BrokerMessage {

    public enum Action { Ack , Publish , Consume , Subscribe};

    Action action ;

    String id;

    String coorelationId;

    int seqNum;

    String topic ;


    String payload;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoorelationId() {
        return coorelationId;
    }

    public void setCoorelationId(String coorelationId) {
        this.coorelationId = coorelationId;
    }

    public int getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "BrokerMessage{" +
                "action=" + action +
                ", id='" + id + '\'' +
                ", coorelationId='" + coorelationId + '\'' +
                ", seqNum=" + seqNum +
                ", topic='" + topic + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}

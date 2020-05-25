package data;

import java.util.Objects;

public class StatusTuple
{
    String id;
    Status status;

    Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public StatusTuple(String id, Status status) {
        this.id = id;
        this.status = status;
    }

    public StatusTuple(String id, Status status, Result result) {
        this.id = id;
        this.status = status;
        this.result = result;
    }

    public StatusTuple(String id) {
        this.id = id;
    }

    public StatusTuple() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusTuple that = (StatusTuple) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "StatusTuple{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", result=" + result +
                '}';
    }
}

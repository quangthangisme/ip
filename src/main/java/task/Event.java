package task;

public class Event extends Task {
    private String fromTime;
    private String toTime;

    public Event(String name, String fromTime, String toTime) {
        super(name);
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + this.fromTime + " to: " + this.toTime + ")";
    }
}
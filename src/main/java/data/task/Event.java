package data.task;

import java.util.List;

public class Event extends Task {
    private String startTime;
    private String endTime;

    public static final String SIGNATURE = "E";

    public Event(String name, String startTime, String endTime) {
        super(name, SIGNATURE);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String toString() {
        return super.toString()
                + " (from: " + this.startTime + " to: " + this.endTime + ")";
    }

    public List<String> encodedAddedInfo() {
        return List.of(this.startTime, this.endTime);
    }
}
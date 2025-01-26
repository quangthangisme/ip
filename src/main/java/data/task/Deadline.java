package data.task;

import java.util.List;

public class Deadline extends Task {
    private String deadline;

    public static final String SIGNATURE = "D";

    public Deadline(String name, String deadline) {
        super(name, SIGNATURE);
        this.deadline = deadline;
    }

    public String toString() {
        return super.toString() + " (by: " + this.deadline + ")";
    }

    public List<String> encodedAddedInfo() {
        return List.of(this.deadline);
    }
}

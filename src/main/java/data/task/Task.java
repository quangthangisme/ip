package data.task;

import java.util.List;

public abstract class Task {
    private String name;
    private boolean isDone;
    private String signature;

    public Task(String name, String signature) {
        this.name = name;
        this.isDone = false;
        this.signature = signature;
    }

    public String toString() {
        return "[" + this.signature + "][" + (this.isDone ? "X" : " ") + "] "
                + this.name;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String encode() {
        StringBuilder encodedTaskBuilder = new StringBuilder();
        encodedTaskBuilder.append(this.signature);
        encodedTaskBuilder.append("\t").append(this.isDone ? 1 : 0);
        encodedTaskBuilder.append("\t").append(this.name);
        encodedAddedInfo().forEach(info
                -> encodedTaskBuilder.append("\t").append(info));
        return encodedTaskBuilder.toString();
    }

    public abstract List<String> encodedAddedInfo();
}

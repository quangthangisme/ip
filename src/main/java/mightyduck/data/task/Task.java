package mightyduck.data.task;

import java.util.List;

public abstract class Task {
    private final String name;
    private boolean isDone;
    private final String signature;

    public Task(String name, String signature) {
        this.name = name;
        this.isDone = false;
        this.signature = signature;
    }

    public String toString() {
        return "[" + this.signature + "][" + (this.isDone ? "X" : " ") + "] "
                + this.name;
    }

    public String getName() {
        return this.name;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public boolean isMarked() {
        return this.isDone;
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

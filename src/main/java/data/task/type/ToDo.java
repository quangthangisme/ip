package data.task.type;

import data.task.Task;

import java.util.List;

public class ToDo extends Task {
    public static final String SIGNATURE = "T";

    public ToDo(String name) {
        super(name, SIGNATURE);
    }

    @Override
    public List<String> encodedAddedInfo() {
        return List.of();
    }
}

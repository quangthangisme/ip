package mightyduck.data.task.type;

import java.util.List;

import mightyduck.data.task.Task;

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

package data.task;

import java.util.List;

public class ToDo extends Task {
    public static final String SIGNATURE = "T";

    public ToDo(String name) {
        super(name, SIGNATURE);
    }

    public List<String> encodedAddedInfo() {
        return List.of();
    }
}

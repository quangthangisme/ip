package command;

import data.TaskManager;
import exception.InvalidValueException;

public abstract class Command {
    protected TaskManager taskManager;

    public Command(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public abstract CommandResult execute() throws InvalidValueException;

    public abstract boolean isBye();
}

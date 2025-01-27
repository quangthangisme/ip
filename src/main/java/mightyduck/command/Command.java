package mightyduck.command;

import mightyduck.data.task.TaskManager;
import mightyduck.exception.InvalidValueException;

public abstract class Command {
    protected TaskManager taskManager;

    public Command(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public abstract CommandResult execute() throws InvalidValueException;

    public abstract boolean isBye();
}

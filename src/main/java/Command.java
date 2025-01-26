import data.TaskManager;
import data.task.Deadline;
import data.task.Event;
import data.task.Task;
import data.task.ToDo;

public enum Command {
    LIST {
        @Override
        public void execute(String argument, TaskManager taskManager,
                            Printer printer) {
            printer.displayTasks(taskManager.getTasks());
        }
    },
    MARK {
        @Override
        public void execute(String argument, TaskManager taskManager,
                            Printer printer) {
            int index = parseIndex(argument);
            Task task = taskManager.markTask(index);
            printer.displayTaskMarked(task, true);
        }
    },
    UNMARK {
        @Override
        public void execute(String argument, TaskManager taskManager,
                            Printer printer) {
            int index = parseIndex(argument);
            Task task = taskManager.unmarkTask(index);
            printer.displayTaskMarked(task, false);
        }
    },
    TODO {
        @Override
        public void execute(String argument, TaskManager taskManager,
                            Printer printer) {
            if (argument.isBlank()) {
                throw new IllegalArgumentException("A todo task must have " +
                        "a description!");
            }
            Task todo = new ToDo(argument);
            taskManager.addTask(todo);
            printer.displayTaskAdded(todo, taskManager.getTaskCount());
        }
    },
    DEADLINE {
        @Override
        public void execute(String argument, TaskManager taskManager,
                            Printer printer) {
            String[] parts = argument.split(" /by ", 2);
            if (parts.length < 2) {
                throw new IllegalArgumentException("Please use: " +
                        "deadline <name> /by <deadline>");
            }
            Task deadline = new Deadline(parts[0], parts[1]);
            taskManager.addTask(deadline);
            printer.displayTaskAdded(deadline, taskManager.getTaskCount());
        }
    },
    EVENT {
        @Override
        public void execute(String argument, TaskManager taskManager,
                            Printer printer) {
            String[] parts = argument.split(" /from ", 2);
            if (parts.length < 2 || !parts[1].contains(" /to ")) {
                throw new IllegalArgumentException("Please use: " +
                        "event <name> /from <start> /to <end>");
            }
            String[] times = parts[1].split(" /to ", 2);
            Task event = new Event(parts[0], times[0], times[1]);
            taskManager.addTask(event);
            printer.displayTaskAdded(event, taskManager.getTaskCount());
        }
    },
    DELETE {
        @Override
        public void execute(String argument, TaskManager taskManager,
                Printer printer) {
            int index = parseIndex(argument);
            Task task = taskManager.deleteTask(index);
            printer.displayTaskDeleted(task, taskManager.getTaskCount());
        }
    };

    public abstract void execute(String argument, TaskManager taskManager,
                                 Printer printer);

    protected int parseIndex(String argument) {
        try {
            return Integer.parseInt(argument) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The task number is invalid: "
                    + argument);
        }
    }
}

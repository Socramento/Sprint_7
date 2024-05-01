package app.taskmanager;


import app.enums.HistoryManager;
import app.intefaces.TaskManager;
import app.history.InMemoryHistoryManager;
import app.history.InMemoryTaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}

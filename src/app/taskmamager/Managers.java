package app.taskmamager;

import app.enums.HistoryManager;
import app.intefaces.TaskManager;
import app.tasks.InMemoryHistoryManager;
import app.tasks.InMemoryTaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}

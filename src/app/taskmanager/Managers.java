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
/*Managers (Класс):
Этот класс предоставляет методы для создания экземпляров менеджеров задач и истории.
Сейчас используются InMemoryTaskManager и InMemoryHistoryManager,
которые предоставляют реализацию менеджеров задач и истории, хранящих данные в памяти.
 */
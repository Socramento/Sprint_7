package app.main;

import app.enums.HistoryManager;
import app.history.FileBackedTaskManager;
import app.history.InMemoryHistoryManager;
import app.intefaces.TaskManager;
import app.taskmanager.Managers;
import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        TaskManager taskManager = Managers.getDefault();
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();
        HistoryManager historyManager = Managers.getDefaultHistory();

        String test = "id, type, name, description, status\n" +
                "1, TASK, Задача 1, Цельная, NEW\n" +
                "3, SUBTASK, Подзадача 5, часть Эпика 1, NEW\n" +
                "2, EPIC, Эпик   1, Раздельная, NEW";



        Task task1 = new Task("Задача 1", "Цельная");
        Task task2 = new Task("Задача 2", "Цельная");
        Epic epic1 = new Epic("Эпик   1", "Раздельная");
        Epic epic2 = new Epic("Эпик   2", "Раздельная");

        fileBackedTaskManager.addTask(task1);

        fileBackedTaskManager.addEpic(epic1);


        Subtask subtask5 = new Subtask("Подзадача 5", "часть Эпика 1", epic1);


        fileBackedTaskManager.addSubtask(subtask5);

    }
}

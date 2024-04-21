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

//        File proverka = new File("C:\\Users\\Maik\\MAIK-GIT\\ТЗ-5\\java-kanban\\text.csv");
//        System.out.println(Files.readString(proverka.toPath())); // способ чтения файлов


        Task task1 = new Task("Задача 1", "Цельная");
        Task task2 = new Task("Задача 2", "Цельная");
        Epic epic1 = new Epic("Эпик   1", "Раздельная");
        Epic epic2 = new Epic("Эпик   2", "Раздельная");

        fileBackedTaskManager.addTask(task1);
//        fileBackedTaskManager.addTask(task2);
        fileBackedTaskManager.addEpic(epic1);
//        fileBackedTaskManager.addEpic(epic2);
//        taskManager.addTask(task2);
//        taskManager.addEpic(epic1);
//        taskManager.addEpic(epic2);
//        taskManager.addTask(task1);

        Subtask subtask5 = new Subtask("Подзадача 5", "часть Эпика 1", epic1);
//        Subtask subtask6 = new Subtask("Подзадача 6", "часть Эпика 1", epic1);
//        Subtask subtask7 = new Subtask("Подзадача 7", "для Э-1", epic3);
//        Subtask subtask8 = new Subtask("Подзадача 8", "Ч. Эпика 2", epic2);
//        Subtask subtask9 = new Subtask("Подзадача 9", "для Э-2", epic4);

        fileBackedTaskManager.addSubtask(subtask5);

//        Task returnTaskFromFile = fileBackedTaskManager.fromString("1, TASK, Задача 1, Цельная, NEW");
//        System.out.println(returnTaskFromFile);
        //FileBackedTaskManager.historyFromString(test);


//        taskManager.addSubtask(subtask5);
//        taskManager.addSubtask(subtask6);
//        taskManager.addSubtask(subtask7);
//        taskManager.addSubtask(subtask8);
//        taskManager.addSubtask(subtask9);
//
//
//        fileBackedTaskManager.findTask(task2);
//          fileBackedTaskManager.findTask(task1);
//          fileBackedTaskManager.findTask(task2);
//        taskManager.findEpic(epic4);
//        taskManager.findEpic(epic3);
//        taskManager.findSubtask(subtask5);
//        taskManager.findSubtask(subtask6);
//        taskManager.findSubtask(subtask7);

//String hist = FileBackedTaskManager.historyToString(historyManager);
//        System.out.println(hist);
//        taskManager.findTask(task2);
//        taskManager.findEpic(epic4);
//        taskManager.findEpic(epic3);
//        //taskManager.removeTask(task1);
//        //taskManager.removeEpic(epic3);
//        //taskManager.removeSubtask(subtask5);
//
//        System.out.println(taskManager.getHistory());

    }
}

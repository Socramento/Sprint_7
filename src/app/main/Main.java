package app.main;

import app.enums.HistoryManager;
import app.enums.Status;
import app.history.FileBackedTaskManager;
import app.history.InMemoryHistoryManager;
import app.intefaces.TaskManager;
import app.taskmanager.Managers;
import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();
        HistoryManager historyManager = Managers.getDefaultHistory();

        Task task1 = new Task("Задача 1", "Цельная", Duration.ofMinutes(30), null);
        Task task2 = new Task("Задача 2", "Цельная", Duration.ofMinutes(30), LocalDateTime.of(2024, Month.JUNE, 25, 18, 0));

        Epic epic1 = new Epic("Эпик   1", "Раздельная");
//        Epic epic2 = new Epic("Эпик   2", "Раздельная");

        fileBackedTaskManager.addTask(task1);
        fileBackedTaskManager.addTask(task2);
        fileBackedTaskManager.addEpic(epic1);
////        fileBackedTaskManager.addEpic(epic2);
////        taskManager.addTask(task2);
////        taskManager.addEpic(epic1);
////        taskManager.addEpic(epic2);
////        taskManager.addTask(task1);
////
        Subtask subtask1 = new Subtask("Подзадача 5", "часть Эпика 1", epic1, Duration.ofMinutes(2), null);
        Subtask subtask2 = new Subtask("Подзадача 6", "часть Эпика 1", epic1, Duration.ofMinutes(30), LocalDateTime.of(2024, Month.JUNE, 25, 12, 10));
        subtask2.setStatus(Status.IN_PROGRESS);
        Subtask subtask3 = new Subtask("Подзадача 7", "часть Эпика 1", epic1, Duration.ofMinutes(3), LocalDateTime.of(2024, Month.JUNE, 25, 18, 10));
        Subtask subtask4 = new Subtask("Подзадача 8", "часть Эпика 1", epic1, Duration.ofMinutes(12), LocalDateTime.of(2024, Month.JUNE, 25, 11, 55));
        //        Subtask subtask8 = new Subtask("Подзадача 8", "Ч. Эпика 2", epic2);
//        Subtask subtask9 = new Subtask("Подзадача 9", "для Э-2", epic4);

        fileBackedTaskManager.addSubtask(subtask1);
        fileBackedTaskManager.addSubtask(subtask2);
        fileBackedTaskManager.addSubtask(subtask3);
        fileBackedTaskManager.addSubtask(subtask4);

        fileBackedTaskManager.getPrioritizedTasks();

//        Task returnTaskFromFile = fileBackedTaskManager.fromString("1, TASK, Задача 1, Цельная, NEW");
//        System.out.println(returnTaskFromFile);
//        FileBackedTaskManager.historyFromString(test);


//        taskManager.addSubtask(subtask1);
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

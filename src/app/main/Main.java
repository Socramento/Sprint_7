package app.main;

import app.intefaces.TaskManager;
import app.taskmanager.Managers;
import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Задача 1    ", " Целая  ");
        Task task2 = new Task("Задача 2    ", " Целая  ");
        Epic epic3 = new Epic("Эпик 3", " --> ");
        Epic epic4 = new Epic("Эпик 4", " -->");

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic3);
        taskManager.addEpic(epic4);
        taskManager.addTask(task1);

        Subtask subtask5 = new Subtask("Подзадача 5", "для Э-1", epic3);
        Subtask subtask6 = new Subtask("Подзадача 6", "для Э-1", epic3);
        Subtask subtask7 = new Subtask("Подзадача 7", "для Э-1", epic3);
        Subtask subtask8 = new Subtask("Подзадача 8", "для Э-2", epic4);
        Subtask subtask9 = new Subtask("Подзадача 9", "для Э-2", epic4);


        taskManager.addSubtasks(subtask5);
        taskManager.addSubtasks(subtask6);
        taskManager.addSubtasks(subtask7);
        taskManager.addSubtasks(subtask8);
        taskManager.addSubtasks(subtask9);


        taskManager.findTask(task1);
        taskManager.findTask(task2);
        taskManager.findEpic(epic4);
        taskManager.findEpic(epic3);
        taskManager.findSubtask(subtask5);
        taskManager.findSubtask(subtask6);
        taskManager.findSubtask(subtask7);
        taskManager.findTask(task1);
        taskManager.findTask(task2);
        taskManager.findEpic(epic4);
        taskManager.findEpic(epic3);
        //taskManager.removeTask(task1);
        //taskManager.removeEpic(epic3);
        //taskManager.removeSubtask(subtask5);

        System.out.println(taskManager.getHistory());

    }
}

package app.main;

import app.intefaces.TaskManager;
import app.taskmamager.Managers;
import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Задача 1    ", " Целая  ");
        Task task2 = new Task("Задача 2    ", " Целая  ");
        Epic epic3 = new Epic("Эпик 1 ####", " ПЕРВЫЙ ");
        Epic epic4 = new Epic("Эпик 2 @@@@", " ВТОРОЙ");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic3);
        taskManager.addEpic(epic4);

        Subtask subtask5 = new Subtask("###  1 ###", "для ПЕРВОГО", epic3);
        Subtask subtask6 = new Subtask("###  2 ###", "для ПЕРВОГО", epic3);
        Subtask subtask7 = new Subtask("###  3 ###", "для ПЕРВОГО", epic3);
        Subtask subtask8 = new Subtask("@@@ 1 @@@", "для ВТОРОГО", epic4);
        Subtask subtask9 = new Subtask("@@@ 2 @@@", "для ВТОРОГО", epic4);

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

        System.out.println(taskManager.getHistory());
    }
}

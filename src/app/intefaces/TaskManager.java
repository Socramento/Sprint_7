package app.intefaces;

import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;

import java.util.ArrayList;
import java.util.List;
public interface TaskManager {

    ArrayList<Task> getTasks();

    ArrayList <Epic> getEpics();

    List <Subtask> getSubtasks();

    List<Task> getHistory();

    int addTask(Task task);

    Task findTask(Task task);

    void clearTasks();

    void removeTask(Task task);

    void updateTask(Task task);

    int addEpic(Epic epic);

    Epic findEpic(Epic epic);

    void clearEpics();

    void removeEpic(Epic epic);

    void updateEpic(Epic epic);

    int addSubtask(Subtask subtask);

    Subtask findSubtask(Subtask subtask);

    void clearSubtasks();

    void removeSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);
}

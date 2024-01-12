package app.taskmamager;

import app.enums.Status;
import app.tasks.Epic;
import app.tasks.SubTask;
import app.tasks.Task;

import java.util.ArrayList;
import java.util.Objects;

public class TaskManager {
    ArrayList<Task> listTask;
    ArrayList<Epic> listEpic;
    ArrayList<SubTask> listSubTask;

    protected int taskId = 0;
    protected int epicId = 0;
    protected int subTaskId = 0;

    public TaskManager() {
        listTask = new ArrayList<>();
        listEpic = new ArrayList<>();
        listSubTask = new ArrayList<>();
    }

    public ArrayList<Task> getListTask() {
        return listTask;
    }

    public ArrayList<Epic> getListEpic() {
        return listEpic;
    }

    public ArrayList<SubTask> getListSubTask() {
        return listSubTask;
    }

    public void addTask(Task task) {
        listTask.add(taskId++, task);
    }

    public void addEpic(Epic epic) {
        listEpic.add(epicId++, epic);
    }

    public void addSubTask(SubTask subTask) {
        listSubTask.add(subTaskId++, subTask);

    }

    public Task getTask(Task task) {
        return task;
    }

    public Epic getEpic(Epic epic) {
        return epic;
    }

    public SubTask getSubTask(SubTask subTask) {
        return subTask;
    }

    public void clearTask() {
        listTask.clear();
    }

    public void clearEpic(Status status) {
        listEpic.clear();
        status = Status.NEW;
        System.out.println(status);
    }

    public void clearSubTask() {
        listSubTask.clear();
    }

    public Task getTaskForId(Task task, int taskId) {
        listTask.get(taskId);
        return task;
    }

    public Epic getEpicForId(Epic epic, int epicId) {
        listEpic.get(epicId);
        return epic;
    }

    public SubTask getSubTaskForId(SubTask subTack, int subTaskId) {
        listSubTask.get(subTaskId);
        return subTack;
    }

    public void updateTask(Task task) {
        listTask.add(task);
    }

    public void updateEpic(Epic epic) {
        listEpic.add(epic);
    }
    public void updateSubTask(SubTask subTask) {
        listSubTask.add(subTask);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TaskManager that = (TaskManager) object;
        return taskId == that.taskId && epicId == that.epicId && subTaskId == that.subTaskId && Objects.equals(listTask, that.listTask) && Objects.equals(listEpic, that.listEpic) && Objects.equals(listSubTask, that.listSubTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listTask, listEpic, listSubTask, taskId, epicId, subTaskId);
    }
}

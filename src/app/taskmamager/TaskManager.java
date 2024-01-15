package app.taskmamager;

import app.enums.Status;
import app.tasks.Epic;
import app.tasks.SubTask;
import app.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TaskManager {
    HashMap<Integer, Task> listTask;
    HashMap<Integer, Epic> listEpic;
    HashMap<Integer, SubTask> listSubTask;
    protected Status status;
    protected int taskId = 0;
    protected int epicId = 0;
    protected int subTaskId = 0;

    public TaskManager() {
        listTask = new HashMap<>();
        listEpic = new HashMap<>();
        listSubTask = new HashMap<>();
        //status = Status.NEW;
    }


    public ArrayList<Task> getListTask() {
        return new ArrayList<>(listTask.values());
    }

    public ArrayList<Epic> getListEpic() {
        return new ArrayList<>(listEpic.values());
    }

    public ArrayList<SubTask> getListSubTask() {
        return new ArrayList<>(listSubTask.values());
    }


    public void clearTask() {
        listTask.clear();
    }

    public void clearEpic() {
        listSubTask.clear();
        listEpic.clear();

    }

    public void clearSubTask() {
        listSubTask.clear();

    }


    public Task getTaskById(Task task) {
        return listTask.get(task.getId());
    }

    public Epic getEpicById(Epic epic) {
        return listEpic.get(epic.getId());
    }

    public SubTask getSubTaskById(SubTask subTask) {

        return listSubTask.get(subTask.getId());
    }


    public void addTask(Task task) {
        listTask.put(++taskId, task);
        task.setStatus(Status.DONE);
    }

    public void addEpic(Epic epic) {
        listEpic.put(++epicId, epic);
        epic.calculateEpicStatus();
    }

    public void addSubTask(SubTask subTask) {
        listSubTask.put(++subTaskId, subTask);
        subTask.setStatus(Status.DONE);

    }


    public void updateTask(Task task) {
        clearTask();
        listTask.put(task.getId(), task);
        task.setStatus(Status.DONE);
    }

    public void updateEpic(Epic epic) {
        clearEpic();
        listEpic.put(epic.getId(), epic);
    }

    public void updateSubTask(SubTask subTask) {
        clearSubTask();
        listSubTask.put(subTask.getId(), subTask);
        subTask.setStatus(Status.DONE);
    }


    public void removeTaskById(Task task) {
        listTask.remove(task.getId(), task);
        task.setStatus(Status.NEW);
    }

    public void removeEpicById(Epic epic) {
        listTask.remove(epic.getId(), epic);
        epic.setStatus(Status.NEW);
    }

    public void removeSubTaskById(SubTask subTask) {
        listTask.remove(subTask.getId(), subTask);
        subTask.setStatus(Status.NEW);
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

package app.taskmamager;

import app.enums.Status;
import app.tasks.Epic;
import app.tasks.SubTask;
import app.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TaskManager   {
    HashMap<Integer, Task> listTask  =  new HashMap<>();;
    HashMap<Integer, Epic> listEpic = new HashMap<>();
    HashMap<Integer, SubTask> listSubTask = new HashMap<>();

    //protected Status status;
    protected int id = 0;



    public ArrayList<Task> getListTask() {return new ArrayList<>(listTask.values());}
    public ArrayList<Epic> getListEpic() {
        return new ArrayList<>(listEpic.values());
    }
    public ArrayList<SubTask> getListSubTask() {
        return new ArrayList<>(listSubTask.values());
    }

    //////////////////////////////УДАЛЕНИЕ//////////////////////////////////////
    public void clearTask() {
        listTask.clear();
    }

    public void clearEpic() {
        listSubTask.clear();
        listEpic.clear();
    }

    public void clearSubTask() {
        listSubTask.clear();
        for (Epic elEpic : listEpic.values()) {
            elEpic.setStatus(Status.NEW);
        }
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

    public int addTask(Task task) {
        task.setId(++id);
        listTask.put(task.getId(), task);
        return task.getId();
    }

    public int addEpic(Epic epic) {
        epic.setId(++id);
        calculateEpicStatus(epic);
        listEpic.put(epic.getId(), epic);
        return epic.getId();
    }
    public int addSubTask(SubTask subTask) {
        subTask.setId(++id);
        Epic epic = listEpic.get(subTask.getEpicId());
        epic.getSubTaskInEpic().add(subTask);
        listSubTask.put(subTask.getId(), subTask);
        calculateEpicStatus(epic);
        return subTask.getId();
    }

    public void updateTask(Task task) {
        listTask.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        listEpic.put(epic.getId(), epic);
    }

    public void updateSubTask(SubTask subTask) {
        listSubTask.put(subTask.getId(), subTask);
        calculateEpicStatus(listEpic.get(subTask.getEpicId()));
    }

    public void removeTaskById(Task task) {
        listTask.remove(task.getId(), task);
    }

    public void removeEpicById(Epic epic) {
        ArrayList<SubTask> epicSubTask = epic.getSubTaskInEpic();
        listEpic.remove(epic.getId());
        listSubTask.values().removeAll(epicSubTask);
    }

    public void removeSubTaskById(SubTask subTask) {
        listSubTask.remove(subTask.getId(),subTask);
        Epic epic = listEpic.get(subTask.getEpicId());
        epic.getSubTaskInEpic().remove(subTask);
        calculateEpicStatus(epic);

    }

    public void calculateEpicStatus(Epic epic) {
        int allDoneCount = 0;
        int allNewCount = 0;

        for (SubTask el : epic.epicSubTask) {
            if (el.getStatus() == Status.DONE) {
                allDoneCount++;
            }
            if (el.getStatus() == Status.NEW) {
                allNewCount++;
            }
        }

        if (allNewCount == epic.epicSubTask.size() || epic.epicSubTask.isEmpty()) {
            epic.setStatus(Status.NEW);
        } else if (allDoneCount == epic.epicSubTask.size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TaskManager that = (TaskManager) object;
        return id == that.id && Objects.equals(listTask, that.listTask) && Objects.equals(listEpic, that.listEpic) && Objects.equals(listSubTask, that.listSubTask) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(listTask, listEpic, listSubTask,  id);
    }
}

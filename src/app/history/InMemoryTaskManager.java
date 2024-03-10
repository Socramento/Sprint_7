package app.history;

import app.enums.HistoryManager;
import app.enums.Status;
import app.intefaces.TaskManager;
import app.taskmanager.Managers;
import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected HashMap<Integer, Task> taskList = new HashMap<>();
    protected HashMap<Integer, Epic> taskEpics = new HashMap<>();
    protected HashMap<Integer, Subtask> listSubtasks = new HashMap<>();
    protected int id = 0;
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public List<Task> getHistory() {return historyManager.getHistory();}

    @Override
    public ArrayList <Task> getTasks() {return new ArrayList<>(taskList.values());}

    @Override
    public ArrayList <Epic> getEpics() {return new ArrayList<>(taskEpics.values());}

    @Override
    public ArrayList <Subtask> getSubtasks() {return new ArrayList<>(listSubtasks.values());}

    @Override
    public int addTask(Task task) {
        task.setId(++id);
        taskList.put(task.getId(), task);
        return task.getId();
    }
    @Override
    public int addEpic(Epic epic) {
        epic.setId(++id);
        updateEpicStatus(epic);
        taskEpics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public int addSubtasks(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (taskEpics.containsKey(epicId)) {
            Epic epic = taskEpics.get(epicId);
            subtask.setId(++id);
            epic.getListSubtask().add(subtask);
            listSubtasks.put(subtask.getId(), subtask);
            updateEpicStatus(epic);
        }
        return subtask.getId();
    }

    @Override
    public  Task findTask(Task task) {
        if (taskList.containsKey(task.getId())) {
            historyManager.add(task); /***/
        }
        return taskList.get(task.getId());
    }
    @Override
    public Epic findEpic(Epic epic) {
        if (taskEpics.containsKey(epic.getId())) {
            historyManager.add(epic);/***/
        }
        return taskEpics.get(epic.getId());
    }
    @Override
    public Subtask findSubtask(Subtask subtask) {
        if (listSubtasks.containsKey(subtask.getId())) {
            historyManager.add(subtask);/***/
        }
        return listSubtasks.get(subtask.getId());
    }

    @Override
    public void clearTasks() {
        taskList.clear();
    }
    @Override
    public void clearEpics() {
        taskEpics.clear();
        listSubtasks.clear();
    }
    @Override
    public void clearSubtasks() {
        listSubtasks.clear();
        for (Epic epic : taskEpics.values()) {
            epic.setStatus(Status.NEW);
        }
    }

    @Override
    public void removeTask(Task task) {
        taskList.remove(task.getId());
        historyManager.remove(task.getId());
    }
    @Override
    public void removeEpic(Epic epic) {
        taskEpics.remove(epic.getId());
        epic.getListSubtask().clear();
        ArrayList<Subtask> listSubtask = getSubtasks();
        for (Subtask subtask : listSubtask) {
            if (subtask.getEpicId() == epic.getId()) {
                listSubtasks.remove(subtask.getId());
            }
        }
        historyManager.remove(epic.getId());
    }
    @Override
    public void removeSubtask(Subtask subtask) {
        listSubtasks.remove(subtask.getId());
        Epic epic = taskEpics.get(subtask.getEpicId());
        epic.getListSubtask().remove(subtask);
        updateEpicStatus(epic);
        historyManager.remove(subtask.getId());
    }

    @Override
    public void updateTask(Task task) {
        taskList.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (taskEpics.containsKey(epic.getId())) {
            taskEpics.put(epic.getId(), epic);
        }
    }
    @Override
    public void updateSubtask(Subtask subtask) {
        if (listSubtasks.containsKey(subtask.getId()) && taskEpics.containsKey(subtask.getEpicId())
                && !taskEpics.containsKey(subtask.getId()) && !listSubtasks.containsKey(subtask.getEpicId())) {
            listSubtasks.put(subtask.getId(), subtask);
            updateEpicStatus(taskEpics.get(subtask.getEpicId()));
        }
    }

    public void updateEpicStatus(Epic epic) {
        if (!epic.getListSubtask().isEmpty()) {
            int statusNew = 0;
            int statusDone = 0;
            for (Subtask subtask : epic.getListSubtask()) {
                switch (subtask.getStatus()) {
                    case NEW:
                        ++statusNew;
                        break;
                    case DONE:
                        ++statusDone;
                        break;
                    default:
                }
            }
            if (statusNew == epic.getListSubtask().size()) {
                epic.setStatus(Status.NEW);

            } else if (statusDone == epic.getListSubtask().size()) {
                epic.setStatus(Status.DONE);

            } else if (epic.getStatus() != Status.IN_PROGRESS) {
                epic.setStatus(Status.IN_PROGRESS);
            }
        } else {
            epic.setStatus(Status.NEW);
        }
    }
}

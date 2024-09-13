package app.history;

import app.enums.HistoryManager;
import app.enums.Status;
import app.enums.TypeTES;
import app.intefaces.TaskManager;
import app.taskmanager.Managers;
import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected HashMap<Integer, Task> listTask = new HashMap<>();
    protected HashMap<Integer, Epic> listEpics = new HashMap<>();
    protected HashMap<Integer, Subtask> listSubtasks = new HashMap<>();
    protected int id = 0;
    final HistoryManager historyManager = Managers.getDefaultHistory();

    Comparator<Task> comparator = Comparator.comparing(Task::getStartTime);
    TreeSet<Task> sortTaskST = new TreeSet<>(comparator);


    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        //sortTaskST.forEach(System.out::println);
        return sortTaskST;
    }

    public void intersection(Task task) {
        boolean hasIntersection = sortTaskST.stream()
                .anyMatch(el -> el.getStartTime().isBefore(task.getStartTime()) & el.getEndTime().isAfter(task.getStartTime()));

        if (hasIntersection) {
            System.out.println("ПЕРЕСЕЧЕНИЕ");
        } else {
            System.out.println("Пересечения нет!");
        }
    }
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(listTask.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(listEpics.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(listSubtasks.values());
    }

    @Override
    public int addTask(Task task) {
        task.setId(++id);
        listTask.put(task.getId(), task);
        if (task.getStartTime() != null) {
            sortTaskST.add(task);
            //intersection(task);
        }
        return task.getId();
    }

    @Override
    public int addEpic(Epic epic) {
        epic.type = TypeTES.EPIC;
        epic.setId(++id);
        updateEpicStatus(epic);
        listEpics.put(epic.getId(), epic);

        return epic.getId();
    }

    @Override
    public int addSubtask(Subtask subtask) {
        subtask.type = TypeTES.SUBTASK;
        subtask.setId(++id);
        Epic epic = listEpics.get(subtask.getEpicId());

        if (epic != null) {
            epic.getListSubtask().add(subtask);
            listSubtasks.put(subtask.getId(), subtask);
            LocalDateTime startTimeSubtask = subtask.getStartTime();
            if (startTimeSubtask != null) {
                sortTaskST.add(subtask);
                intersection(subtask);
            }
            updateEpicStatus(epic);
            return subtask.getId();
        } else {
            return -1;
        }
    }

    @Override
    public Task findTask(Task task) {
        if (listTask.containsKey(task.getId())) {
            historyManager.add(task);
        }
        return listTask.get(task.getId());
    }

    @Override
    public Epic findEpic(Epic epic) {
        if (listEpics.containsKey(epic.getId())) {
            historyManager.add(epic);
        }
        return listEpics.get(epic.getId());
    }

    @Override
    public Subtask findSubtask(Subtask subtask) {
        if (listSubtasks.containsKey(subtask.getId())) {
            historyManager.add(subtask);
        }
        return listSubtasks.get(subtask.getId());
    }

    @Override
    public void clearTasks() {
        listTask.clear();
    }

    @Override
    public void clearEpics() {
        listEpics.clear();
        listSubtasks.clear();
    }

    @Override
    public void clearSubtasks() {
        listSubtasks.clear();
        listSubtasks.values().forEach(epic -> epic.setStatus(Status.NEW));
    }

    @Override
    public void removeTask(Task task) {
        listTask.remove(task.getId());
        historyManager.remove(task.getId());
    }

    @Override
    public void removeEpic(Epic epic) {
        listEpics.remove(epic.getId());
        epic.getListSubtask().clear();
        ArrayList<Subtask> listSubtask = getSubtasks();
        listSubtask.removeIf(subtask -> subtask.getEpicId() == epic.getId());

        historyManager.remove(epic.getId());
    }

    @Override
    public void removeSubtask(Subtask subtask) {
        if (listSubtasks.isEmpty()) {
            System.out.println("Подзадачи отсутствуют");
        } else {
            listSubtasks.remove(subtask.getId());
            Epic epic = listEpics.get(subtask.getEpicId());
            epic.getListSubtask().remove(subtask);
            updateEpicStatus(epic);
            historyManager.remove(subtask.getId());
        }
    }

    @Override
    public void updateTask(Task task) {
        listTask.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (listEpics.containsKey(epic.getId())) {
            listEpics.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (listSubtasks.containsKey(subtask.getId())
                && listEpics.containsKey(subtask.getEpicId())
                && !listEpics.containsKey(subtask.getId())
                && !listSubtasks.containsKey(subtask.getEpicId())) {

            listSubtasks.put(subtask.getId(), subtask);

            updateEpicStatus(listEpics.get(subtask.getEpicId()));
        }
    }


    public void updateEpicStatus(Epic epic) {
        if (epic.getListSubtask().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        long statusNew = epic.getListSubtask().stream()
                .filter(subtask -> subtask.getStatus() == Status.NEW)
                .count();

        long statusDone = epic.getListSubtask().stream()
                .filter(subtask -> subtask.getStatus() == Status.DONE)
                .count();
            if (statusNew == epic.getListSubtask().size()) {
                epic.setStatus(Status.NEW);

            } else if (statusDone == epic.getListSubtask().size()) {
                epic.setStatus(Status.DONE);

            } else if (epic.getStatus() != Status.IN_PROGRESS) {
                epic.setStatus(Status.IN_PROGRESS);
            }
    }
}

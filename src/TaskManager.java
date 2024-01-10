import java.util.HashMap;
import java.util.Objects;

public class TaskManager {
    HashMap<Integer, Task> listTask; // создаем ХэшТаблицу
    HashMap<Integer, Epic> listEpic;
    HashMap<Integer, SubTask> listSubTask;

    protected int taskId = 100;
    protected int epicId = 200;
    protected int subTaskId = 300;

    public TaskManager() { // создаем конструктор без параметров, для передачи в другие классы
        listTask = new HashMap<>();
        listEpic = new HashMap<>();
        listSubTask = new HashMap<>();
    }

    /////////////////////////////////// ГЕТТЕРЫ //////////////////////////////////////////
    public HashMap<Integer, Task> getListTask() {
        return listTask;
    }

    public HashMap<Integer, Epic> getListEpic() {
        return listEpic;
    }

    public HashMap<Integer, SubTask> getListSubTask() {
        return listSubTask;
    }

    /////////////////////////////////// ДОБАВЛЕНИЕ ОБЪЕКТА ////////////////////////////////
    public void addTask(Task task) {
        listTask.put(taskId++, task);
    }

    public void addEpic(Epic epic) {
        listEpic.put(epicId++, epic);
    }

    public void addSubTask(SubTask subTask) {
        listSubTask.put(subTaskId++, subTask);
    }

    /////////////////////////////////// ПОЛУЧЕНИЕ СПИСКА ВСЕХ ЗАДАЧ ////////////////////////////////
    public Task getTask(Task task) {
        listTask.get(task);
        return task;
    }

    public Epic getEpic(Epic epic) {
        listEpic.get(epic);
        return epic;
    }

    public SubTask getSubTask(SubTask subTask) {
        listSubTask.get(subTask);
        return subTask;
    }

    /////////////////////////////////// УДАЛЕНИЕ ВСЕХ ЗАДАЧ ////////////////////////////////
    public void clearTask() {
        listTask.clear();
        taskId = 0;
    }

    public void clearEpic() {
        listEpic.clear();
        epicId = 0;
    }

    public void clearSubTask() {
        listSubTask.clear();
        subTaskId = 0;
    }

    /////////////////////////////////// ПОЛУЧЕНИЕ ПО ИДЕНТИФИКАТОРУ ////////////////////////////////
    public Task getTask_forId(Task task, int taskId) {
        listTask.get(taskId);
        return task;
    }

    public Epic getEpic_forId(Epic epic, int epicId) {
        listEpic.get(epicId);
        return epic;
    }

    public SubTask getSubTask_forId(SubTask subTack, int subTaskId) {
        listSubTask.get(subTaskId);
        return subTack;
    }

    /////////////////////////////////// ПОЛУЧЕНИЕ УНИКАЛЬНОГО НОМЕРА ////////////////////////////////
    public int getId_Task() {
        listTask.get(taskId);
        return taskId;
    }

    public int getId_Epic() {
        listEpic.get(epicId);
        return epicId;
    }

    /////////////////////////////////// ОБНОВЛЕНИЕ ДАННЫХ ////////////////////////////////
    public void updateTask(Task task) {
        listTask.put(task.getId_Task(), task);
    }

    public void updateEpic(Epic epic) {
        listEpic.put(epic.getId_Task(), epic);
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

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    ArrayList<SubTask> saveSubTask;
    private static int epicIdCounter = 200;

    public Epic(String title, String description, Status status) {
        super(title, description, status); // вызываем конструктор Task и наследуем поля
        saveSubTask = new ArrayList<>();
        setId(epicIdCounter++);
    }

    public int getId() {
        return super.id;
    }

    public void addSubTaskInEpic(SubTask subTack) {
        saveSubTask.add(subTack);
    }

    public Status calculateEpicStatus() {
        boolean allDone = true;
        boolean allNew = true;

        for (SubTask el : saveSubTask) {
            if (el.getStatus() != Status.DONE) {
                allDone = false;
            }
            if (el.getStatus() != Status.NEW) {
                allNew = false;
            }
        }

        if (allNew || saveSubTask.isEmpty()) {
            return Status.NEW;
        } else if (allDone) {
            return Status.DONE;
        } else {
            return Status.IN_PROGRESS;
        }
    }

    public void clearSubTask(SubTask subTack) {
        saveSubTask.clear();
    }

    public SubTask saveSubTask(SubTask subTack) {
        saveSubTask.get(subTaskId);
        return subTack;
    }

    public SubTask deleteSubTask(SubTask subTack) {
        saveSubTask.remove(subTaskId);
        return subTack;
    }
}

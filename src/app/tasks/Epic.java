package app.tasks;

import app.enums.Status;

import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<SubTask> saveSubTask;


    public Epic(String title, String description, Status status) {
        super(title, description, status);
        saveSubTask = new ArrayList<>();
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

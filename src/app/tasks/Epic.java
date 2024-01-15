package app.tasks;

import app.enums.Status;

import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<SubTask> epicSubTask;

    public Epic(String title, String description) {
        super(title, description);
        epicSubTask = new ArrayList<>();
    }

    public int getId() {
        return super.id;
    }

    public void addSubTaskInEpic(SubTask subTack) {
        epicSubTask.add(subTack);
        subTack.setStatus(Status.DONE);
    }

    public ArrayList<SubTask> getSubTaskInEpic() {
        calculateEpicStatus();
        return epicSubTask;
    }

    public void clearSubTaskInEpic(SubTask subTack) {
        epicSubTask.clear();
        subTack.setStatus(Status.NEW);
    }

    public void removeSubTaskById(SubTask subTask) {
        epicSubTask.remove(subTask.getId());
        setStatus(Status.NEW);
    }

    public void calculateEpicStatus() {
        int allDoneCount = 0;
        int allNewCount = 0;

        for (SubTask el : epicSubTask) {
            if (el.getStatus() == Status.DONE) {
                allDoneCount++;
            }
            if (el.getStatus() == Status.NEW) {
                allNewCount++;
            }
        }

        if (allNewCount == epicSubTask.size() || epicSubTask.isEmpty()) {
            status = Status.NEW;
        } else if (allDoneCount == epicSubTask.size()) {
            status = Status.DONE;
        } else {
            status = Status.IN_PROGRESS;
        }
    }
}

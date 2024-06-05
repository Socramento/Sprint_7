package app.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Subtask> subList;
    protected LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
        subList = new ArrayList<>();
        this.startTime = getStartTime();
        this.endTime = getEndTime();
        this.duration = Duration.between(endTime, startTime);
    }

    public LocalDateTime getStartTime() {
        LocalDateTime startTime = LocalDateTime.MAX;
        for (Subtask subtask : subList) {
            LocalDateTime min = subtask.getStartTime();
            if (min.isBefore(startTime)) {
                startTime = min;
            }
        }
        return startTime;//
    }

    @Override
    public LocalDateTime getEndTime() {

        LocalDateTime endT = LocalDateTime.MIN;
        for (Subtask subtask : subList) {
            LocalDateTime startTime = subtask.getStartTime();
            if (startTime != null) {
                LocalDateTime maxEnd = startTime.plus(subtask.duration);
                if (maxEnd.isAfter(endTime)) {
                    endT = maxEnd;
                }
            }
        }
        return endT;
    }

    public ArrayList<Subtask> getListSubtask() {

        return subList;
    }


    @Override
    public String toString() {
        return "ЭПИК" + ", ЗАВЕРШЕНИЕ = " +  getEndTime() + "\n";
    }
}

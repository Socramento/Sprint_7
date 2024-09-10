package app.tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    protected int epicId;

    public Subtask(String name, String description, Epic epic, Duration duration, LocalDateTime startTime) {
        super(name, description,duration, startTime );
        epicId = epic.id;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }


//    @Override
//    public String toString() {
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
//        String formattedStartTime = startTime != null ? startTime.plus(duration).format(formatter) : "(Время и дата не введены!)";
//
//        return name +
//                ", " + description +
//                ", " + status +
//                ", " + id +
//                ", " + type +
//                ", " + "Период " + duration.toMinutes() + " минут" +
//                ", " + formattedStartTime +
//                "\n";
//    }
}


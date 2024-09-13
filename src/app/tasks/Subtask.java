package app.tasks;

import app.enums.Status;
import app.enums.TypeTES;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    protected int epicId;

    public Subtask(String name, String description, Epic epic, Duration duration, LocalDateTime startTime) {
        super(name, description,duration, startTime );
        this.epicId = epic.id;
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.type = TypeTES.TASK;
        this.duration = duration;
        this.startTime = startTime;

    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {

        return name +
                ", " + description +
                ", " + status +
                ", " + id +
                ", " + type +
                ", " + duration +
                ", " + startTime +
                        "\n";
    }
}


package app.tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    protected int epicId;

    public Subtask(String name, String detscription, Epic epic, Duration duration, LocalDateTime startTime) {
        super(name, detscription,duration, startTime );
        epicId = epic.id;

    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}


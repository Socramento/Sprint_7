package app.tasks;

import app.enums.Status;
import app.enums.TypeTES;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected Status status;
    protected int id;
    public TypeTES type;
    protected Duration duration;
    protected LocalDateTime startTime;

    public Task(String name, String description, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.type = type;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public LocalDateTime getEndTime(){//дата и время завершения задачи, которые рассчитываются исходя из startTime и duration
        return startTime.plus(duration);
    }
    public Duration getDuration() {
        return duration;
    }
    public LocalDateTime getStartTime() {
            return startTime;
    }
    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTypeTES(TypeTES type) {/** Под вопросом*/
        this.type = type;
    }

    public TypeTES getTypeTES() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

@Override
public String toString() {
    return
             name + '\'' +
            ", " + description + '\'' +
            ", " + status +
            ", " + id +
            ", " + type +
            ", " + duration +
            " минут, " + startTime +
             "\n";
}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(id, otherTask.id);
    }


}

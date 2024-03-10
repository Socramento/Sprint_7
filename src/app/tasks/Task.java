package app.tasks;

import app.enums.Status;

import java.util.Objects;

public class Task {
    protected String title;
    protected String description;
    protected Status status;
    protected int id;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = Status.NEW;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getName() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.title = name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id = " + id + ", название = " + title + ", описание = " + description + ", статус = " + status + "\n";
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(id, otherTask.id);
    }
}

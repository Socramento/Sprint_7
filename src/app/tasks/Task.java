package app.tasks;

import app.enums.Status;
import app.taskmamager.TaskManager;

public class Task extends TaskManager {
    protected String title;
    protected String description;
    protected Status status;

    public int getId() {
        return id;
    }

    protected int id;
    private static int taskIdCounter = 1;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = Status.NEW;
        setId(taskIdCounter++);
    }

    protected int setId(int id) {
        this.id = id;
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Sprint-4{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}' + "-->";
    }
}

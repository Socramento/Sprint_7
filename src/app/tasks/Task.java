package app.tasks;

import app.enums.Status;

public class Task  {
    protected String title;
    protected String description;
    protected Status status;
    protected int id;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = Status.NEW;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public Status getStatus() {return status;}
    public void setStatus(Status status) {this.status = status;}

    @Override
    public String toString() {
        return
                "назв = '" + title + '\'' +
                ", опис='" + description + '\'' +
                ", стат=" + status +
                ", id=" + id +
                '}' + "-->";
    }
}

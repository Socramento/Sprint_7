package app.tasks;

import app.enums.Status;
import app.enums.TypeTES;

import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected Status status;
    protected int id;
    public TypeTES type;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.type = type;
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

    public TypeTES getTypeTES(){
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        // "id = " + id + TypeTES.values() + ", название = " + name + ", описание = " + description + ", статус = " + status + "\n";

        return id + ", " + type + ", " + name + ", " + description + ", " + status + "\n";
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(id, otherTask.id);
    }
}

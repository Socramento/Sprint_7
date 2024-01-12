package app.tasks;

import app.enums.Status;

public class SubTask extends Task {


    public SubTask(String title, String description, Status status) {
        super(title, description, status);

    }

    public int getId() {
        return super.id;
    }

}

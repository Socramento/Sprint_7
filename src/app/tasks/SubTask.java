package app.tasks;

public class SubTask extends Task {
    public SubTask(String title, String description) {
        super(title, description);

    }

    public int getId() {
        return super.id;
    }
}

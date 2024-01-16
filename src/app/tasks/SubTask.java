package app.tasks;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String title, String description) {
        super(title, description);

    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
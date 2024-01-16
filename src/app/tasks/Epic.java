package app.tasks;

import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<SubTask> epicSubTask;

    public Epic(String title, String description) {
        super(title, description);
        epicSubTask = new ArrayList<>();

    }


    public ArrayList<SubTask> getSubTaskInEpic() {return epicSubTask;}
    public int getId() {
        return super.id;
    }
}

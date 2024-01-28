package app.tasks;

import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Subtask> subList;

    public Epic(String name, String description ){
        super(name, description);
        subList = new ArrayList<>();
    }

    public ArrayList<Subtask> getListSubtask() {
        return subList;
    }
}

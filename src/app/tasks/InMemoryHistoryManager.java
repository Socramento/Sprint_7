package app.tasks;

import app.enums.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_LIST_MAX_SIZE = 10;
    protected List<Task> historyList = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return historyList;
    }

    @Override
    public void add(Task task) {
        if (historyList.size() >= HISTORY_LIST_MAX_SIZE)
            historyList.remove(0);
        historyList.add(task);
    }
}

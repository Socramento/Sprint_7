package app.history;

import app.enums.HistoryManager;
import app.enums.Status;
import app.enums.TypeTES;
import app.intefaces.TaskManager;
import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    public File text = new File("text.csv");

    public void save() throws ManagerSaveException {

        try {
            FileWriter fw = new FileWriter(text);
            fw.write(historyToString(historyManager));
            fw.write("id, type, name, description,status, epic, Duration, LocalDateTime \n");

            for (Task task : listTask.values()) {
                fw.write(task.toString());
            }
            for (Epic epic : listEpics.values()) {
                fw.write(epic.toString());
            }
            for (Subtask subtask : listSubtasks.values()) {
                fw.write(subtask.toString());
            }
            fw.close();
            System.out.println("Данные успешно сохранены в файловую систему.");
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка в сохранении");
        }
    }


    public static List<Integer> historyFromString(String value) throws IOException {
        List<Integer> history = new ArrayList<>();

        String[] str = value.split("\n");

        for (int i = 0; i < str.length; i++) {
            String[] parts = str[i].split(", ");

            if (parts[0].equals("id")) {

            } else {
                int id = Integer.parseInt(parts[0].trim());
                history.add(id);
            }
        }
        return history;
    }

    public Task fromString(String value) throws IOException {

        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0].trim());
        TypeTES type = TypeTES.valueOf(parts[1].trim());
        String name = parts[2].trim();
        String description = parts[3].trim();
        Status status = Status.valueOf(parts[4].trim());
        Duration duration = Duration.ofMinutes(Long.parseLong(parts[5].trim()));
        LocalDateTime startTime = LocalDateTime.parse(parts[6].trim());

        Task task = new Task(name, description, duration, startTime);
        task.setStatus(status);
        task.setTypeTES(type);

        return task;
    }

    public static String historyToString(HistoryManager manager) {
        StringBuilder sb = new StringBuilder();
        List<Task> history = manager.getHistory();
        for (Task task : history) {

            sb.append(task.getId()).append(", ")
                    .append(task.getTypeTES()).append(", ")
                    .append(task.getName()).append(", ")
                    .append(task.getDescription()).append(", ")
                    .append(task.getStatus()).append(", ")
                    .append(task.getDuration()).append(", ")
                    .append(task.getStartTime()).append(", ")
                    .append(task.getEndTime()).append("\n")
            ;
        }
        return sb.toString();
    }

    public static FileBackedTaskManager loadFromFile(File file) throws IOException {
        FileReader fr = new FileReader(file);

        BufferedReader bf = new BufferedReader(fr);

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bf.readLine()) != null) {/**ИСПРАВЛЯЛ на !=*/
            sb.append(line);
        }

        return new FileBackedTaskManager();
    }

    @Override
    public int addTask(Task task) {
        task.type = TypeTES.TASK;
        final int id = super.addTask(task);
        save();
        return id;
    }

    @Override
    public int addEpic(Epic epic) {
        epic.type = TypeTES.EPIC;
        final int id = super.addEpic(epic);
        save();
        return id;
    }

    @Override
    public int addSubtask(Subtask subtask) {
        subtask.type = TypeTES.SUBTASK;
        final int id = super.addSubtask(subtask);
        save();
        return id;
    }

    @Override
    public Task findTask(Task task) {
        super.findTask(task);
        save();
        return listTask.get(task.getId());
    }

    @Override
    public Epic findEpic(Epic epic) {
        super.findEpic(epic);
        save();
        return listEpics.get(epic.getId());
    }

    @Override
    public Subtask findSubtask(Subtask subtask) {
        super.findSubtask(subtask);
        save();
        return listSubtasks.get(subtask.getId());
    }

    @Override
    public ArrayList<Task> getTasks() {
        super.getTasks();
        return new ArrayList<>(listTask.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        super.getEpics();
        return new ArrayList<>(listEpics.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        super.getSubtasks();

        return new ArrayList<>(listSubtasks.values());
    }

    @Override
    public void clearTasks() {
        super.clearTasks();
        save();
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        save();
    }

    @Override
    public void clearSubtasks() {
        super.clearSubtasks();
        save();
    }


    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}

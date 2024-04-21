package app.history;

import app.enums.HistoryManager;
import app.enums.Status;
import app.enums.TypeTES;
import app.intefaces.TaskManager;
import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    File text = new File("text.csv");
//    String csv = "1, TASK, Задача 1, Цельная, NEW\n" +
//            "3, SUBTASK, Подзадача 5, часть Эпика 1, NEW\n" +
//            "2, EPIC, Эпик   1, Раздельная, NEW";


    public void save() {

        try {
            FileWriter fw = new FileWriter(text);
            FileReader fr = new FileReader(text);

            fw.write(historyToString(historyManager)); //записываю историю в файл через метод  historyToString(HistoryManager manager)

            fw.write("id, type, name, description,status, epic \n");

            for (Task task : taskList.values()) {
                fw.write(task.toString());
            }

            for (Epic epic : taskEpics.values()) {
                fw.write(epic.toString());
            }

            for (Subtask subtask : listSubtasks.values()) {
                fw.write(subtask.toString());
            }

            fw.close();
            System.out.println("Данные успешно сохранены в файловую систему.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Task fromString(String value) throws IOException {

        String[] parts = value.split(",");
        // Получаем значения из разделенных частей строки
        int id = Integer.parseInt(parts[0].trim());
        TypeTES type = TypeTES.valueOf(parts[1].trim());
        String name = parts[2].trim();
        String description = parts[3].trim();
        Status status = Status.valueOf(parts[4].trim());

        // Создаем и возвращаем новый объект Task
        Task task = new Task(name, description);
        task.setId(id);
        task.setStatus(status);
        task.setTypeTES(type);

        return task;
    }

    public static String historyToString(HistoryManager manager) { //с передачей объекта HistoryManager, который хранит историю задач.
        StringBuilder sb = new StringBuilder();//cоздается пустой StringBuilder для формирования строки в табличном формате.
        // Добавляем заголовок таблицы
        //sb.append("id, type, name, description, status\n");

        // Получаем историю из менеджера истории
        List<Task> history = manager.getHistory();//вызывается метод getHistory() объекта manager
//Этот метод возвращает список задач, хранящихся в истории.
        //Полученный список задач сохраняется в переменной history
        // Проходимся по каждой задаче в истории
        for (Task task : history) {
            // Добавляется заголовок таблицы, определяющий структуру данных: "id, type, name, description, status\n".
            sb.append(task.getId()).append(", ")
                    .append(task.getTypeTES()).append(", ")
                    .append(task.getName()).append(", ")
                    .append(task.getDescription()).append(", ")
                    .append(task.getStatus()).append("\n");
        }
        return sb.toString();
        //После прохода по всем задачам истории, StringBuilder преобразуется в строку с помощью метода toString().
        //Полученная строка, представляющая данные в табличном формате, возвращается из метода historyToString.*/
    }

    static FileBackedTaskManager loadFromFile(File file) throws IOException {
        FileReader fr = new FileReader(file);
        //Создаем объект BufferedReader для более эффективного чтения
        BufferedReader bf = new BufferedReader(fr);
        //Создаем объект StringBuilder для сбора текста в строку
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bf.readLine()) == null) {
            sb.append(line);
        }

        return FileBackedTaskManager.loadFromFile(file);
    }

    public static List<Integer> historyFromString(String value) throws IOException {//восстановления менеджера истории из табличного формата данных
        List<Integer> history = new ArrayList<>();

        String [] str = value.split("\n");// раздели массив по переносу по трем  частям

        for (int i = 0; i < str.length; i++) {//перебираем элементы поочередно
            String[] parts = str[i].split(", ");// каждый элемент разбиваем на части через запятые

            if (parts[0].equals("id")) {
                System.out.println(str[i]);
            } else {
                int id = Integer.parseInt(parts[0].trim()); //берем первое число, парсим
                history.add(id); // добавляем в историю
            }
        }
        System.out.println(history);
        return history;
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
        final int id = super.addTask(subtask);
        save();
        return id;
    }

    ///////////////////////////////////////
    @Override
    public Task findTask(Task task) {
        super.findTask(task);
        save();
        return taskList.get(task.getId());
    }

    @Override
    public Epic findEpic(Epic epic) {
        super.findEpic(epic);
        save();
        return taskEpics.get(epic.getId());
    }

    @Override
    public Subtask findSubtask(Subtask subtask) {
        super.findSubtask(subtask);
        save();
        return listSubtasks.get(subtask.getId());
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

}

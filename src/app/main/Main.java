package app.main;
import app.enums.Status;
import app.taskmamager.TaskManager;
import app.tasks.Epic;
import app.tasks.SubTask;
import app.tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task = new Task("Задача     ", "  Обычная задача (без разделения)", Status.DONE);
        Epic epic = new Epic("Эпик       ", "  Содержит в себе несколько подзадач", Status.DONE);
        SubTask subTack1 = new SubTask("Подзадача_1", "  Составляющая Эпика 1st", Status.DONE);
        SubTask subTack2 = new SubTask("Подзадача_2", "  Составляющая Эпика 2nd", Status.DONE);
        SubTask subTack3 = new SubTask("Подзадача_3", "  Составляющая Эпика 3rd", Status.DONE);

    }
}

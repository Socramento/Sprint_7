package app.main;

import app.history.FileBackedTaskManager;
import app.intefaces.TaskManager;
import app.taskmanager.Managers;
import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class HttpTaskServer {
    private static final int PORT = 8080;
    static TaskManager taskManager = Managers.getDefault();
    static FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();
    static HttpServer httpServer;
    private static boolean isStartServer = false;

    static {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpTaskServer(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public static void main(String[] args) throws IOException {

        Task task1 = new Task("Задача 1", "Цельная", Duration.ofMinutes(30), null);
        Task task2 = new Task("Задача 2", "Цельная", Duration.ofMinutes(30), LocalDateTime.of(2024, Month.JUNE, 25, 18, 0));
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new Epic("Эпик   1", "Раздельная");
        Epic epic2 = new Epic("Эпик   2", "Раздельная");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        Subtask subtask1 = new Subtask("Купить", "часть Эпика 1", epic1, Duration.ofMinutes(2), null);
        Subtask subtask2 = new Subtask("Продать", "часть Эпика 1", epic1, Duration.ofMinutes(30), LocalDateTime.of(2024, Month.JUNE, 25, 12, 10));

        Subtask subtask3 = new Subtask("Обменять", "часть Эпика 2", epic2, Duration.ofMinutes(3), LocalDateTime.of(2024, Month.JUNE, 25, 12, 10));
        Subtask subtask4 = new Subtask("Подарить", "часть Эпика 2", epic2, Duration.ofMinutes(12), LocalDateTime.of(2024, Month.JUNE, 25, 11, 55));

        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);

        startServer();
    }

    public static void startServer()  {

        if (!isStartServer) {
            httpServer.createContext("/tasks", new TaskHandler(taskManager));
            httpServer.createContext("/epics", new EpicHandler(taskManager));
            httpServer.createContext("/subtasks", new SubtaskHandler(taskManager));
            httpServer.createContext("/history", new HistoryHandler(taskManager, fileBackedTaskManager));
            httpServer.createContext("/prioritized", new PriorityHandler(taskManager, fileBackedTaskManager));
            httpServer.start();
            isStartServer = true;
            System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        }
    }

    public static void stopServer() {
        if (isStartServer) {
            httpServer.stop(0);
            System.out.println("HTTP-сервер отключен!");
        }
    }

    public static Gson getGson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        return gson;
    }
}

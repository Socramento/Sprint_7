package app.test;

import app.history.InMemoryTaskManager;
import app.intefaces.TaskManager;
import app.main.HttpTaskServer;
import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class HttpTaskServerTest {

    TaskManager taskManager = new InMemoryTaskManager();
    HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);

    Gson gson = HttpTaskServer.getGson();


    @BeforeEach
    public void setUp() throws InterruptedException {
        taskManager.clearTasks();
        taskManager.clearEpics();
        taskManager.clearSubtasks();

        httpTaskServer.startServer();
        //Thread.sleep(2000);
    }

    @AfterEach
    public void shutDown() {
        taskManager.clearTasks();
        taskManager.clearEpics();
        taskManager.clearSubtasks();
        httpTaskServer.stopServer();
    }

    @Test
    public void testAddTask() throws IOException, InterruptedException {

        Task task = new Task("Test 1", "Testing task 1", Duration.ofMinutes(5), LocalDateTime.now());

        String taskJson = gson.toJson(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode());

        List<Task> tasksFromManager = taskManager.getTasks();
        System.out.println("Кол-во задач добавленное через тест " + tasksFromManager.size());

        Assertions.assertNotNull(tasksFromManager, "Задачи не возвращаются");
        Assertions.assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        Assertions.assertEquals("Test 1", tasksFromManager.get(0).getName(), "Некорректное имя задачи");
    }

    @Test
    public void testAddEpic() throws IOException, InterruptedException {

        Epic epic = new Epic("Test 2", "Testing epic 2");
//        epic.setStatus(Status.NEW);
//        epic.setId(500);
//        epic.setStartTime(LocalDateTime.of(2024, Month.AUGUST, 11, 0, 0));
//
        Subtask subtask5 = new Subtask("Тест Сабтаск 5", "для Эпика", epic, Duration.ofMinutes(3), LocalDateTime.of(2024, Month.JUNE, 25, 12, 10));
        Subtask subtask6 = new Subtask("Тест Сабтаск 6", "для Эпика", epic, Duration.ofMinutes(12), LocalDateTime.of(2024, Month.JUNE, 25, 11, 55));
        taskManager.addSubtask(subtask5);
        taskManager.addSubtask(subtask6);

        String epicJson = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode());

        List<Epic> epicFromManager = taskManager.getEpics();
        System.out.println("Кол-во задач добавленное через тест " + epicFromManager.size());

        Assertions.assertNotNull(epicFromManager, "Задачи не возвращаются");
        Assertions.assertEquals(1, epicFromManager.size(), "Некорректное количество задач");
        Assertions.assertEquals("Test 2", epicFromManager.get(0).getName(), "Некорректное имя задачи");
    }

    @Test
    public void testAddSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("ТестЭпик", "ТестЭпик");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Test 3", "Testing subtask 3", epic, Duration.ofMinutes(5), LocalDateTime.of(2024, Month.JUNE, 25, 11, 55));

        String subtaskJson = gson.toJson(subtask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");

        HttpRequest request3 = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subtaskJson)).build();

        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response3.statusCode());

        List<Subtask> subtasksFromManager = taskManager.getSubtasks();
        System.out.println("Кол-во задач добавленное через тест " + subtasksFromManager.size());

        Assertions.assertNotNull(subtasksFromManager, "Задачи не возвращаются");
        Assertions.assertEquals(1, subtasksFromManager.size(), "Некорректное количество задач");
        Assertions.assertEquals("Test 3", subtasksFromManager.get(0).getName(), "Некорректное имя задачи");
    }
}
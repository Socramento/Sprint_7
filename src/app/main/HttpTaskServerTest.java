package app.main;

import app.history.InMemoryTaskManager;
import app.intefaces.TaskManager;
import app.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.List;

public class HttpTaskServerTest {

    TaskManager taskManager = new InMemoryTaskManager();
    HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();


    @BeforeEach
    public void setUp() throws IOException {
        taskManager.clearTasks();
        taskManager.clearEpics();
        taskManager.clearSubtasks();

        httpTaskServer.startServer();
    }

    @AfterEach
    public void shutDown() {
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
}
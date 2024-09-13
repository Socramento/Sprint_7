package app.main;

import app.intefaces.TaskManager;
import app.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private TaskManager taskManager;
    private List<Task> allTasks;
    Gson gson = HttpTaskServer.getGson();

    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        EndpointTask endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case GET_TASKS:
                handleGetAllTasks(exchange);
                break;
            case GET_TASK_ID:
                handleGetTaskByID(exchange);
                break;
            case POST_CREATE_OR_UPDATE_TASK:
                handlePostCreateTask(exchange);
                break;
            case DELETE_TASK:
                handleDeleteTask(exchange);
                break;
            default:
                sendNotFound(exchange, "ВНИМАНИЕ! Некорректный запрос");
        }
    }

    private void handleGetAllTasks(HttpExchange exchange) throws IOException {
        allTasks = taskManager.getTasks();

        String list = allTasks.stream()
                .map(Task::toString)
                .collect(Collectors.joining("\n"));

        sendText(exchange, list);
    }

    private void handleGetTaskByID(HttpExchange exchange) throws IOException {
        allTasks = taskManager.getTasks();

        Optional<Integer> taskIdOpt = getTaskByID(exchange);

        if (taskIdOpt.isEmpty()) {
            sendNotFound(exchange, "ВНИМАНИЕ! Некорректный идентификатор задачи!");
        }

        int taskId = taskIdOpt.get();

        Optional<Task> taskOpt = allTasks.stream()
                .filter(task -> task.getId() == taskId)
                .findFirst();

        if (taskOpt.isEmpty()) {
            sendNotFound(exchange, "ВНИМАНИЕ! Задача с ID " + taskId + " не найдена!");
            return;
        }

        Task task = taskOpt.get();
        String response = task.toString();
        sendText(exchange, response);
    }


    private void handlePostCreateTask(HttpExchange exchange) throws IOException {
        allTasks = taskManager.getTasks();
        try {
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

            Task task = gson.fromJson(body, Task.class);

            if (task == null) {
                sendNotFound(exchange, "Пустое или некорректное тело запроса");
                return;
            }

            boolean idExist = allTasks.stream()
                    .anyMatch(taskС -> taskС.getId() == task.getId());

            if (idExist) {
                sendHasInteractions(exchange, "ВНИМАНИЕ - ПЕРЕСЕЧЕНИЕ! Задача с ID № " + task.getId() + " уже существует");
                return;
            }
            allTasks.add(task);
            taskManager.addTask(task);

            System.out.println("Кол-во задач добавленное через хендлер " + allTasks.size());

            sendText(exchange, "Задача успешно создана!\n");
        } catch (Exception e) {
            e.printStackTrace();
            sendNotFound(exchange, "Разбирайся дальше. ");
        }
    }

    private void handleDeleteTask(HttpExchange exchange) throws IOException {
        allTasks = taskManager.getTasks();

        Optional<Integer> taskIdOpt = getTaskByID(exchange);
        if (taskIdOpt.isEmpty()) {
            sendNotFound(exchange, "ВНИМАНИЕ!!! Такой задачи не существует");
            return;
        }

        int taskId = taskIdOpt.get();
        Optional<Task> taskOpt = allTasks.stream()
                .filter(task -> task.getId() == taskId)
                .findFirst();

        if (taskOpt.isEmpty()) {
            sendNotFound(exchange, "ВНИМАНИЕ!!! Такой задачи не существует");
            return;
        }

        sendText(exchange, "Задача с ID " + taskId + " УДАЛЕНА!");
        Task killTask = taskOpt.get();
        taskManager.removeTask(killTask);
        allTasks = taskManager.getTasks();
    }


    private Optional<Integer> getTaskByID(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(pathParts[2]));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private EndpointTask getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        if (pathParts.length == 2 && pathParts[1].equals("tasks")) {
            if (requestMethod.equals("GET")) {
                return EndpointTask.GET_TASKS;
            }
        }

        if (pathParts.length == 3 && pathParts[1].equals("tasks")) {
            if (requestMethod.equals("GET")) {
                Optional.of(Integer.parseInt(pathParts[2]));
                return EndpointTask.GET_TASK_ID;
            }
        }

        if (requestMethod.equals("POST")) {
            return EndpointTask.POST_CREATE_OR_UPDATE_TASK;
        }

        if (requestMethod.equals("DELETE")) {
            return EndpointTask.DELETE_TASK;
        }

        return EndpointTask.UNKNOWN;
    }

    enum EndpointTask {GET_TASKS, GET_TASK_ID, POST_CREATE_OR_UPDATE_TASK, DELETE_TASK, UNKNOWN}
}

class TaskListTypeToken extends TypeToken<List<Task>> {
}
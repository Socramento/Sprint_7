package app.main;

import app.intefaces.TaskManager;
import app.tasks.Subtask;
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

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private TaskManager taskManager;
    private List<Subtask> allSubtaskus;
    Gson gson = HttpTaskServer.getGson();

    public SubtaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        EndpointSubtask endpointSubtask = getEndpointSubtask(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpointSubtask) {
            case GET_SUBTASK:
                handleGetAllSubtasks(exchange);
                break;
            case GET_SUBTASK_BY_ID:
                handleGetSubtaskByID(exchange);
                break;
            case POST_SUBTASK:
                handlePostCreateSubtask(exchange);
                break;
            case DELETE_SUBTASK:
                handleDeleteSubtask(exchange);
                break;

            default:
                sendNotFound(exchange, "ВНИМАНИЕ! Некорректный запрос");
        }
    }

    private void handleGetAllSubtasks(HttpExchange exchange) throws IOException {

        allSubtaskus = taskManager.getSubtasks();

        String list = allSubtaskus.stream()
                .map(Subtask::toString)
                .collect(Collectors.joining("\n"));

        sendText(exchange, list);
    }

    private void handleGetSubtaskByID(HttpExchange exchange) throws IOException {
        allSubtaskus = taskManager.getSubtasks();
        try {

            Optional<Integer> subtaskIdOpt = getSubtaskByID(exchange);
            if (subtaskIdOpt.isEmpty()) {
                sendNotFound(exchange, "ВНИМАНИЕ! Некорректный идентификатор задачи!");
            }

            int subtaskId = subtaskIdOpt.get();

            Optional<Subtask> subtaskOpt = allSubtaskus.stream()
                    .filter(subtask -> subtask.getId() == subtaskId)
                    .findFirst();

            if (subtaskOpt.isEmpty()) {
                sendNotFound(exchange, "ВНИМАНИЕ! Задача с ID " + subtaskId + " не найдена!");
                return;
            }

            Subtask subtask = subtaskOpt.get();

            sendText(exchange, subtask.toString());

        } catch (Exception e) {
            e.printStackTrace();
            sendNotFound(exchange, "Какая-то засада");
        }
    }

    private void handlePostCreateSubtask(HttpExchange exchange) throws IOException {
        allSubtaskus = taskManager.getSubtasks();
        try {
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

            Subtask subtask = gson.fromJson(body, Subtask.class);

            if (subtask == null) {
                sendNotFound(exchange, "Пустое или некорректное тело запроса");
                return;
            }

            boolean idExist = allSubtaskus.stream()
                    .anyMatch(subtaskС -> subtaskС.getId() == subtask.getId());

            if (idExist) {
                sendHasInteractions(exchange, "ВНИМАНИЕ - ПЕРЕСЕЧЕНИЕ! Задача с ID № " + subtask.getId() + " уже существует");
                return;
            }
            allSubtaskus.add(subtask);
            taskManager.addSubtask(subtask);

            System.out.println("Кол-во сабтасок добавленное через хендлер " + allSubtaskus.size());
            inputStream.close();
            sendText(exchange, "Задача успешно создана!\n");
        } catch (Exception e) {
            e.printStackTrace();
            sendNotFound(exchange, "Разбирайся дальше. ");
        }
    }

    private void handleDeleteSubtask(HttpExchange exchange) throws IOException {
        allSubtaskus = taskManager.getSubtasks();

        Optional<Integer> subtaskIdOpt = getSubtaskByID(exchange);
        if (subtaskIdOpt.isEmpty()) {
            sendNotFound(exchange, "ВНИМАНИЕ!!! Такой subtask не существует");
            return;
        }

        int subtaskId = subtaskIdOpt.get();
        Optional<Subtask> subtaskOpt = allSubtaskus.stream()
                .filter(subtask -> subtask.getId() == subtaskId)
                .findFirst();

        if (subtaskOpt.isEmpty()) {
            sendNotFound(exchange, "ВНИМАНИЕ!!! Такой subtask не существует");
            return;
        }

        sendText(exchange, "subtask с ID " + subtaskId + " УДАЛЕНА!");

        Subtask killSubtask = subtaskOpt.get();
        taskManager.removeSubtask(killSubtask);
        allSubtaskus.remove(killSubtask);

    }
    public EndpointSubtask getEndpointSubtask(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        if (pathParts.length == 2 && pathParts[1].equals("subtasks")) {
            if (requestMethod.equals("GET")) {
                return EndpointSubtask.GET_SUBTASK;
            }
        }

        if (pathParts.length == 3 && pathParts[1].equals("subtasks")) {
            if (requestMethod.equals("GET")) {
                return EndpointSubtask.GET_SUBTASK_BY_ID;
            }
        }

        if (requestMethod.equals("POST")) {
            return EndpointSubtask.POST_SUBTASK;
        }


        if (requestMethod.equals("DELETE")) {
            return EndpointSubtask.DELETE_SUBTASK;
        }

        return EndpointSubtask.UNKNOWN;
    }

    private Optional<Integer> getSubtaskByID(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(pathParts[2]));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    enum EndpointSubtask {GET_SUBTASK, GET_SUBTASK_BY_ID, POST_SUBTASK, DELETE_SUBTASK, UNKNOWN}
}



class SubtaskListTypeToken extends TypeToken<List<Subtask>> {

}



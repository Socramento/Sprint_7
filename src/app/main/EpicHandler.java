package app.main;

import app.intefaces.TaskManager;
import app.tasks.Epic;
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

public class EpicHandler extends BaseHttpHandler implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private TaskManager taskManager;
    private List<Epic> allEpics;
    Gson gson = HttpTaskServer.getGson();

    public EpicHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        EndpointEpic endpointEpic = getEndpointEpic(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpointEpic) {
            case GET_EPIC:
                handleGetAllEpics(exchange);
                break;
            case GET_EPIC_BY_ID:
                handleGetEpicByID(exchange);
                break;
            case POST_EPIC:
                handlePostCreateEpic(exchange);
                break;
            case DELETE_EPIC:
                handleDeleteEpic(exchange);
                break;
            default:
                sendNotFound(exchange, "ВНИМАНИЕ! Некорректный запрос");
        }
    }

    private void handleGetAllEpics(HttpExchange exchange) throws IOException {
        allEpics = taskManager.getEpics();
        String list = taskManager.getEpics().stream()
                .map(Epic::toString)
                .collect(Collectors.joining("\n"));

        sendText(exchange, list);
    }

    private void handleGetEpicByID(HttpExchange exchange) throws IOException {
        allEpics = taskManager.getEpics();

        Optional<Integer> epicIdOpt = getEpicByID(exchange);

        if (epicIdOpt.isEmpty()) {
            sendNotFound(exchange, "ВНИМАНИЕ! Некорректный идентификатор задачи!");
        }

        int epicId = epicIdOpt.get();

        Optional<Epic> epicOpt = allEpics.stream()
                .filter(epic -> epic.getId() == epicId)
                .findFirst();

        if (epicOpt.isEmpty()) {
            sendNotFound(exchange, "ВНИМАНИЕ! Задача с ID " + epicId + " не найдена!");
            return;
        }

        Epic epic = epicOpt.get();
        String response = epic.toString();
        sendText(exchange, response);
    }



    private void handlePostCreateEpic(HttpExchange exchange) throws IOException {
        allEpics = taskManager.getEpics();
        try {
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

            Epic epic = gson.fromJson(body, Epic.class);

            if (epic == null) {
                sendNotFound(exchange, "Пустое или некорректное тело запроса");
                return;
            }

            boolean idExist = allEpics.stream()
                    .anyMatch(epicС -> epicС.getId() == epic.getId());

            if (idExist) {
                sendHasInteractions(exchange, "ВНИМАНИЕ - ПЕРЕСЕЧЕНИЕ! Эпик с ID № " + epic.getId() + " уже существует");
                return;
            }
            allEpics.add(epic);
            taskManager.addEpic(epic);

            System.out.println("Кол-во эпиков добавленное через хендлер " + allEpics.size());

            sendText(exchange, "Задача успешно создана!\n");
        } catch (Exception e) {
            e.printStackTrace();
            sendNotFound(exchange, "Разбирайся дальше. ");
        }
    }




    private void handleDeleteEpic(HttpExchange exchange) throws IOException {
        allEpics = taskManager.getEpics();

        Optional<Integer> epicIdOpt = getEpicByID(exchange);
        if (epicIdOpt.isEmpty()) {
            sendNotFound(exchange, "ВНИМАНИЕ!!! Такой задачи не существует");
            return;
        }

        int epicId = epicIdOpt.get();
        Optional<Epic> epicOpt = allEpics.stream()
                .filter(epic -> epic.getId() == epicId)
                .findFirst();

        if (epicOpt.isEmpty()) {
            sendNotFound(exchange, "ВНИМАНИЕ!!! Такой задачи не существует");
            return;
        }

        sendText(exchange, "Задача с ID " + epicId + " УДАЛЕНА!");
        Epic killEpic = epicOpt.get();
        taskManager.removeEpic(killEpic);
        allEpics = taskManager.getEpics();
    }

    private Optional<Integer> getEpicByID(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(pathParts[2]));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public EndpointEpic getEndpointEpic(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        if (pathParts.length == 2 && pathParts[1].equals("epics")) {
            if (requestMethod.equals("GET")) {
                return EndpointEpic.GET_EPIC;
            }
        }

        if (pathParts.length == 3 && pathParts[1].equals("epics")) {
            if (requestMethod.equals("GET")) {
                Optional.of(Integer.parseInt(pathParts[2]));
                return EndpointEpic.GET_EPIC_BY_ID;
            }
        }

        if (requestMethod.equals("POST")) {
            return EndpointEpic.POST_EPIC;
        }

        if (requestMethod.equals("DELETE")) {
            return EndpointEpic.DELETE_EPIC;
        }
        return EndpointEpic.UNKNOWN;
    }

    enum EndpointEpic {GET_EPIC, GET_EPIC_BY_ID, POST_EPIC, DELETE_EPIC, UNKNOWN}
}

class EpicListTypeToken extends TypeToken<List<Epic>> {

}
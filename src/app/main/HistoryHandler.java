package app.main;

import app.history.FileBackedTaskManager;
import app.intefaces.TaskManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final FileBackedTaskManager fileBackedTaskManager;

    public HistoryHandler(TaskManager taskManager, FileBackedTaskManager fileBackedTaskManager) {
        this.taskManager = taskManager;
        this.fileBackedTaskManager = fileBackedTaskManager;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        EndpointHistory endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case GET_HISTORY:
                handleGetHistory(exchange);
                break;
            default:
                sendNotFound(exchange, "ВНИМАНИЕ! Некорректный запрос");
        }
    }

    private void handleGetHistory(HttpExchange exchange) throws IOException {

        sendDecodeText(exchange, "История просмотров сохраненная в файл TEXT" + "\n");

        StringBuilder fromFileText = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileBackedTaskManager.text))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fromFileText.append(line).append("\n");


            }
        } catch (IOException e) {
            fromFileText.append("Ошибка при чтении файла: ").append(e.getMessage());
        }

        // Отправка содержимого файла в ответе
        byte[] bytes = fromFileText.toString().getBytes("UTF-8");
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
//        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
//        exchange.sendResponseHeaders(200, 0);
//
//        // Преобразование истории в JSON
//        Gson gson = new Gson();
//        String jsonHistory = gson.toJson(fileBackedTaskManager.getHistory());
//
//        // Отправка JSON в ответе
//        byte[] bytes = jsonHistory.getBytes("UTF-8");
//        OutputStream os = exchange.getResponseBody();
//        os.write(bytes);
//        os.close();
    }

    private EndpointHistory getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        if (requestMethod.equals("GET")) {
            return EndpointHistory.GET_HISTORY;
        }

        return EndpointHistory.UNKNOWN;
    }

    enum EndpointHistory {GET_HISTORY, UNKNOWN}
}

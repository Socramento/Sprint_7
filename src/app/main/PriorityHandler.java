package app.main;

import app.history.FileBackedTaskManager;
import app.intefaces.TaskManager;
import app.tasks.Task;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PriorityHandler extends BaseHttpHandler implements HttpHandler {

        private final TaskManager taskManager;
        private final FileBackedTaskManager fileBackedTaskManager;

        public PriorityHandler(TaskManager taskManager, FileBackedTaskManager fileBackedTaskManager) {
            this.taskManager = taskManager;
            this.fileBackedTaskManager = fileBackedTaskManager;
        }


        @Override
        public void handle(HttpExchange exchange) throws IOException {

            EndpointPrioritized endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

            switch (endpoint) {
                case GET_PRIORITIZED:
                    handlePrioritized(exchange);
                    break;
                default:
                    sendNotFound(exchange, "ВНИМАНИЕ! Некорректный запрос");
            }
        }

        private void handlePrioritized(HttpExchange exchange) throws IOException {
            TreeSet<Task>  allPrioritized = taskManager.getPrioritizedTasks();

            String list = allPrioritized.stream()
                    .map(Task::toString)
                    .collect(Collectors.joining("\n"));

            sendText(exchange, list);
        }

        private EndpointPrioritized getEndpoint(String requestPath, String requestMethod) {
            String[] pathParts = requestPath.split("/");

            if (requestMethod.equals("GET")) {
                return EndpointPrioritized.GET_PRIORITIZED;
            }

            return EndpointPrioritized.UNKNOWN;
        }

        enum EndpointPrioritized {GET_PRIORITIZED, UNKNOWN}
    }

